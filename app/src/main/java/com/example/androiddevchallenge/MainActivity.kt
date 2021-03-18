/*
 * Copyright 2021 Paulo Pereira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Curtain(
            foldingDuration = 400,
            mainCell = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.DarkGray,
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(text = "This is the main cell!", color = Color.White)
                    }
                }
            },
            foldCells = listOf(
                {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(128.dp),
                        backgroundColor = Color.DarkGray,
                        shape = RectangleShape
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Text(text = "This is the first folded cell!", color = Color.White)
                        }
                    }
                },
                {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(128.dp),
                        backgroundColor = Color.DarkGray,
                        shape = RectangleShape
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Text(text = "This is the second folded cell!", color = Color.White)
                        }
                    }
                },
                {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(128.dp),
                        backgroundColor = Color.DarkGray,
                        shape = RectangleShape
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        ) {
                            Text(text = "This is the third folded cell!", color = Color.White)
                        }
                    }
                }
            )
        )
    }
}
