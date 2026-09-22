<h1 align="center">Continue</h1>

<p align="center">Pioneering open-source coding agent</p>

<div align="center">

<a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache_2.0-blue.svg" /></a>
<a href="https://docs.continue.dev"><img src="https://img.shields.io/badge/Docs-docs.continue.dev-blue" /></a>
<a href="https://github.com/continuedev/continue/releases"><img src="https://img.shields.io/badge/Changelog-GitHub_Releases-blue" /></a>

</div>

<p align="center">
  <img src="media/github-readme.png" alt="Banner" />
</p>

## Fork: Distill

This is a personal fork of [Continue](https://github.com/continuedev/continue), focused on **agent transparency** in the VS Code extension: seeing exactly what the model is about to do (tool arguments) and what it received back (tool output).

### Features

| Feature                      | Description                                                                                                                                                                                                               |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 🛠️ **MCP Tool Args Display** | A toggle icon (code brackets) next to each tool call shows the tool's arguments inline, e.g. `SearchWeb(query: "react hooks", url: "https://...")`. Makes it easy to verify what the LLM is about to do before approving. |
| 📤 **Tool Output Display**   | A toggle icon (document) next to each tool call shows the full response received from the tool (web search results, command output, ...). Makes it easy to analyze/debug the agent's behavior after execution.            |
| 📊 **Token Count**           | The context usage indicator now shows the percentage of context used (e.g. "85% of context filled") alongside the existing context bar.                                                                                   |

> All changes are purely UI enhancements — the underlying tool calls and MCP protocol are not modified.

### Changelog

#### 1.4.0-preview.1

Based on upstream `continuedev/continue` (package version 1.3.40).

- **Added** — MCP Tool Args Display: toggle to show tool call arguments inline before approval
- **Added** — Tool Output Display: toggle to show the full response received from a tool after execution
- **Added** — Token Count: context usage percentage shown alongside the context bar

## What is Continue?

> _Note: The `continuedev/continue` repository is no longer actively maintained and is read-only for all users._

Continue is a coding agent available as a [CLI](#cli), [VS Code extension](#vs-code), and [JetBrains plugin](#jetbrains).

## Documentation

To learn how to configure Continue, how it works, and how to customize it, check out the [Continue Docs](https://docs.continue.dev).

## Final 2.0.0 Release

We polished Continue and did a final 2.0.0 release of the VS Code extension, CLI, and JetBrains plugin.

This included removing anonymous telemetry, pulling out authentication, squashing bugs, and more.

### VS Code

[![VS Code Marketplace](https://img.shields.io/badge/VS_Code_Marketplace-007ACC?logo=visualstudiocode&logoColor=white)](https://marketplace.visualstudio.com/items?itemName=Continue.continue) [![OpenVSX Registry](https://img.shields.io/badge/OpenVSX_Registry-C160EF?logo=eclipseide&logoColor=white)](https://open-vsx.org/extension/Continue/continue) [![View source](https://img.shields.io/badge/View_source-181717?logo=github&logoColor=white)](extensions/vscode)

### CLI

[![npm](https://img.shields.io/badge/npm-CB3837?logo=npm&logoColor=white)](https://www.npmjs.com/package/@continuedev/cli) [![View source](https://img.shields.io/badge/View_source-181717?logo=github&logoColor=white)](extensions/cli)

### JetBrains

> _Note: We recommend using the Continue CLI instead of the JetBrains plugin._

[![GitHub Releases](https://img.shields.io/badge/GitHub_Releases-181717?logo=github&logoColor=white)](https://github.com/continuedev/continue/releases) [![View source](https://img.shields.io/badge/View_source-181717?logo=github&logoColor=white)](extensions/intellij)

## Contributors

Thank you to the entire Continue community for helping us create a pioneering coding agent.

What we built together pushed the boundaries of what AI developer tooling could be.

We hope this codebase continues to serve as a foundation for others.

## Code friends

<a href="https://github.com/continuedev/continue/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=continuedev/continue&max=500" />
</a>

## How to build

### GUI

```powershell
cd gui
npm run build
```

### VSCode extension

```powershell
cd extensions/vscode
npm install
npm run prepackage
npm run esbuild
npm run package
```

## How to install

```powershell
code --install-extension extensions/vscode/build/continue-1.4.0-preview.1.vsix
```

## Dev mode

```powershell
# Terminal 1 - watch GUI
cd gui
npm run dev
```

```powershell
# Terminal 2 - watch extension
cd extensions/vscode
npm run esbuild-watch
```

Then, go to `extensions/vscode` and press F5.

## Original Repository

[continuedev/continue](https://github.com/continuedev/continue)

## License

Apache 2.0 © 2023-2026 Continue Dev, Inc.
