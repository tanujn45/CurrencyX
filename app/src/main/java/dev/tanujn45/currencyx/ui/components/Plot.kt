package dev.tanujn45.currencyx.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import dev.tanujn45.currencyx.utils.CurrencyPoint
import dev.tanujn45.currencyx.utils.usdJan2023

@Composable
fun LineChart(
    modifier: Modifier = Modifier, points: List<CurrencyPoint> = usdJan2023
) {
    if (points.isEmpty()) {
        Box(
            modifier = modifier.background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    RoundedCornerShape(12.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Chart data loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    val maxValue = points.maxOf { it.value }
    val minValue = points.minOf { it.value }
    val valueRange = if (maxValue - minValue == 0f) 1f else maxValue - minValue
    val lineColor = MaterialTheme.colorScheme.primary

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val chartWidth = size.width
            val chartHeight = size.height
            val spacing = chartWidth / (points.size - 1).toFloat()

            // Draw grid lines
            val gridColor = Color.Gray.copy(alpha = 0.2f)
            val gridLines = 4
            repeat(gridLines) { i ->
                val y = (chartHeight / (gridLines - 1)) * i
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(chartWidth, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Build the line path
            val linePath = Path().apply {
                points.forEachIndexed { index, point ->
                    val x = index * spacing
                    val y = chartHeight - ((point.value - minValue) / valueRange) * chartHeight
                    if (index == 0) moveTo(x, y) else lineTo(x, y)
                }
            }

            /*   // Create fill path
               val fillPath = Path().apply {
                   addPath(linePath)
                   lineTo(chartWidth, chartHeight)
                   lineTo(0f, chartHeight)
                   close()
               }

               // Draw gradient fill
               drawPath(
                   path = fillPath,
                   brush = Brush.verticalGradient(
                       colors = listOf(
                           lineColor.copy(alpha = 0.3f),
                           lineColor.copy(alpha = 0.05f)
                       ),
                       startY = 0f,
                       endY = chartHeight
                   )
               )*/

            // Draw the line
            drawPath(
                path = linePath, color = lineColor, style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                    pathEffect = PathEffect.cornerPathEffect(6.dp.toPx())
                )
            )

            // Draw data points
            /* points.forEachIndexed { index, point ->
                 val x = index * spacing
                 val y = chartHeight - ((point.value - minValue) / valueRange) * chartHeight

                 // Outer circle
                 drawCircle(
                     color = lineColor,
                     radius = 6.dp.toPx(),
                     center = Offset(x, y)
                 )
                 // Inner circle
                 drawCircle(
                     color = Color.White,
                     radius = 3.dp.toPx(),
                     center = Offset(x, y)
                 )
             }*/
        }
    }
}
