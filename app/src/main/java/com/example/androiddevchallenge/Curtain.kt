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

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Curtain(
    foldingDuration: Int = 250,
    mainCell: @Composable () -> Unit,
    foldCells: List<@Composable () -> Unit>
) {
    val foldScope = rememberCoroutineScope()
    var isOpened by remember { mutableStateOf(false) }
    var isTransitionRunning by remember { mutableStateOf(false) }

    fun toggleCurtain() {
        if (!isTransitionRunning) {
            isTransitionRunning = true
            isOpened = !isOpened

            foldScope.launch {
                delay(foldingDuration.toLong() * foldCells.size)
                isTransitionRunning = false
            }
        }
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable { toggleCurtain() }
    ) {
        MainCell(
            isOpened = isOpened,
            cellsQuantity = foldCells.size,
            foldingDuration = foldingDuration,
            content = mainCell
        )
        FoldedCells(
            isOpened = isOpened,
            foldingDuration = foldingDuration,
            foldCells = foldCells
        )
    }
}

@Composable
private fun MainCell(
    isOpened: Boolean,
    cellsQuantity: Int,
    foldingDuration: Int,
    content: @Composable () -> Unit
) {
    val mainCellTransition = updateTransition(targetState = isOpened)

    val mainCellAlpha by mainCellTransition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 100,
                delayMillis = if (isOpened) 0 else foldingDuration * cellsQuantity
            )
        }
    ) { state ->
        when (state) {
            false -> 1f
            true -> 0f
        }
    }

    Box(modifier = Modifier.alpha(mainCellAlpha)) {
        content()
    }
}

@Composable
private fun FoldedCells(
    isOpened: Boolean,
    foldingDuration: Int,
    foldCells: List<@Composable () -> Unit>
) {
    Column {
        foldCells.forEachIndexed { index, cell ->
            FoldedCell(
                isOpened = isOpened,
                cellsQuantity = foldCells.size,
                foldingDuration = foldingDuration,
                index = index,
                content = cell
            )
        }
    }
}

@Composable
private fun FoldedCell(
    isOpened: Boolean,
    cellsQuantity: Int,
    foldingDuration: Int,
    index: Int,
    content: @Composable () -> Unit
) {
    var cellMaxHeight by remember { mutableStateOf(0.dp) }
    val transition = updateTransition(targetState = isOpened)
    val foldingDelay = if (isOpened) foldingDuration * index else foldingDuration * (cellsQuantity - index)

    val rotationValue by transition.animateFloat(transitionSpec = { tween(durationMillis = foldingDuration, delayMillis = foldingDelay) }) { state ->
        when (state) {
            false -> 180f
            true -> 0f
        }
    }
    val alphaValue by transition.animateFloat(transitionSpec = { tween(durationMillis = foldingDuration, delayMillis = foldingDelay) }) { state ->
        when (state) {
            false -> 0f
            true -> 1f
        }
    }
    val sizeValue by transition.animateFloat(transitionSpec = { tween(durationMillis = foldingDuration, delayMillis = foldingDelay) }) { state ->
        when (state) {
            false -> 0.dp.value
            true -> cellMaxHeight.value
        }
    }

    Layout(
        content = content,
        modifier = Modifier
            .graphicsLayer {
                alpha = alphaValue
                rotationX = rotationValue
            }
    ) { measurables, constraints ->
        val contentPlaceable = measurables[0].measure(constraints)
        cellMaxHeight = contentPlaceable.height.dp
        layout(contentPlaceable.width, sizeValue.toInt()) {
            contentPlaceable.place(0, 0)
        }
    }
}
