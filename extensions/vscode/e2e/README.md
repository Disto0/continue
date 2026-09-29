# E2E Tests

## Setup

When running e2e tests for the first time

```bash
npm run e2e:all
```

## Run

Depending on what code you update, you can use a faster loop to test your changes:

- If you update the e2e test code and/or config.yaml/json, you can run `npm run e2e:quick`
- If you update the extension code, you can run `npm run e2e:recompile`
- If you update the gui code, you can run `npm run e2e:rebuild-gui`

## Writing tests

All e2e tests are separated (by folder) into

- `selectors` - functions that return elements
- `actions` - functions that perform actions on the editor
- `tests` - the actual tests, which are typically longer paths of functionality rather than individual actions

### Why are my tests failing?

- Did you place a `data-testid` on a React component instead of an actual HTML element?
- Do you have a config.yaml locally but the test is failing when running with config.json in CI?
- Is your `data-testid` or selector actually just wrong?
- Are you inconsistently getting different behaviors? You can try adding a `TestUtils.waitForTimeout` between two events if you think it's caused by a race condition. Note that this may lead to flake down the road.
- Alternatively, you can add a `TestUtils.waitForSuccess`

### How is it connecting to the LLM?

- This depends on the model provider written inside the `test-continue/config.json` and `test-continue/config.yaml`. If the model provider is `mock`, it will use `MockLLM`, and if it uses `test`, it will use `TestLLM`.

### Quirks

- For non-macOS systems, `codesign` is not available. You should run `npm run e2e:all-non-mac` instead of `npm run e2e:all`.

## VS Code / extension-tester version pinning — READ BEFORE TOUCHING E2E DEPS

> [!WARNING]
> The e2e toolchain is **deliberately pinned** and must stay internally consistent. Changing one piece in
> isolation silently breaks every editor test with `no such element: .native-edit-context`. Do not "just update"
> `vscode-extension-tester` (or run a bare `npm install -u`) without reading this.

### The three versions that must move together

| Package                          | Pinned to                  | Where                                             |
| -------------------------------- | -------------------------- | ------------------------------------------------- |
| `vscode-extension-tester`        | `8.14.1` (exact, no caret) | `package.json` devDependencies                    |
| `@redhat-developer/locators`     | `1.12.1`                   | `package.json` `overrides`                        |
| `@redhat-developer/page-objects` | `1.12.1`                   | `package.json` `overrides`                        |
| VS Code binary under test        | `1.95.0`                   | `e2e:get-vscode` / `e2e:get-chromedriver` scripts |

The `overrides` for the two `@redhat-developer/*` packages are **required**: extest declares them as `^1.12.1`,
so a plain install would drift them to the latest `1.x` even when extest itself is pinned.

### Why it breaks (root cause, so we never re-investigate this)

- The e2e scripts download the VS Code **1.95.0** binary (`--code_version 1.95.0`).
- But `extest run-tests` gets **no** `--code_version`, so it resolves the locator set against VS Code **"latest"**.
- Newer `@redhat-developer/locators` (>= ~1.24) map the editor input to `.native-edit-context` for VS Code
  **>= 1.101.0**, and keep `.inputarea` only for older versions. VS Code 1.95.0 has no `.native-edit-context`
  element, so every test that types into the editor fails with `no such element`.
- With locators pinned to **1.12.1** there is no `.native-edit-context` mapping at all — editor input always uses
  `.inputarea`, which matches the 1.95.0 binary. That is why 1.12.1 is the "known good" pin.

### If you want to go to a modern VS Code instead of staying pinned

Pick **one** version in the range supported by your extest (`package.json` → `supportedVersions`) and set it
**consistently everywhere**: `e2e:get-vscode`, `e2e:get-chromedriver`, **and add `--code_version <v>` to
`e2e:test`** (that last one is the piece that was missing). Then you _can_ use newer locators, because a modern
binary really does expose `.native-edit-context`. Verify locally with `npm run e2e:quick` before pushing — CI
uses `npm ci`, so commit the regenerated `package-lock.json` too.

### Local note

CI installs from the lockfile (`npm ci`). After changing these pins, run `npm ci` (not `npm install`) locally to
reproduce exactly what CI gets.
