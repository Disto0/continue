package com.github.continuedev.continueintellijextension

import com.automation.remarks.junit5.Video
import com.intellij.driver.sdk.ui.components.*
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.NoProject
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File

class Autocomplete {

    @Video
    @Test
    fun testAutocomplete() {
        val starter = Starter.newContext("testExample", TestCase(IdeProductProvider.IC, NoProject).withVersion("2024.3"))
        PluginConfigurator(starter).installPluginFromFolder(File(System.getProperty("CONTINUE_PLUGIN_DIR")))
        starter.runIdeWithDriver().useDriverAndCloseIde {
            welcomeScreen {
                createNewProjectButton.click()
                button("Create").click()
            }
            ideFrame {
                editorTabs {
                    clickTab("Main.java")
                }
                codeEditor {
                    keyboard {
                        enterText("TEST_USER_MESSAGE_0")
                        space()
                    }
                    // Poll for the completion response instead of a fixed 2 s wait (flaky on cold runners).
                    // The Driver SDK (Starter 243) only exposes `UiComponent.wait(Duration): T` (no lambda),
                    // so we poll the editor text with a plain loop until the response appears or 30 s elapse.
                    val deadline = System.currentTimeMillis() + 30_000L
                    while (!text.contains("TEST_LLM_RESPONSE_0") && System.currentTimeMillis() < deadline) {
                        Thread.sleep(500)
                    }
                    assertTrue(text.contains("TEST_LLM_RESPONSE_0"))
                    keyboard {
                        tab()
                    }
                    // After accepting the suggestion with Tab, the response text must be present in the editor.
                    assertTrue(text.contains("TEST_LLM_RESPONSE_0"))
                }
            }
        }
    }

}