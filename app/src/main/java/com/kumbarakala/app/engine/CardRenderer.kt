package com.kumbarakala.app.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.kumbarakala.app.data.model.Artisan
import com.kumbarakala.app.data.model.Product
import com.kumbarakala.app.data.model.StoryCard
import com.kumbarakala.app.util.ImageUtils

/**
 * Engine that renders story card images with product photos, AI text, and branding.
 * Output: 1080 x 1350 px JPEG (4:5 ratio — WhatsApp/Instagram optimized)
 *
 * All templates use dynamic text positioning based on actual text height
 * to prevent overlapping issues.
 */
object CardRenderer {

    private const val CARD_WIDTH = 1080
    private const val CARD_HEIGHT = 1350

    /**
     * Render a complete story card image.
     */
    fun renderCard(
        productPhoto: Bitmap?,
        product: Product,
        storyCard: StoryCard,
        artisan: Artisan
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(CARD_WIDTH, CARD_HEIGHT, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        when (storyCard.templateStyle) {
            "Classic" -> renderClassic(canvas, productPhoto, product, storyCard, artisan)
            "Modern" -> renderModern(canvas, productPhoto, product, storyCard, artisan)
            "Heritage" -> renderHeritage(canvas, productPhoto, product, storyCard, artisan)
            "Eco" -> renderEco(canvas, productPhoto, product, storyCard, artisan)
            else -> renderClassic(canvas, productPhoto, product, storyCard, artisan)
        }

        return bitmap
    }

    // ==================== CLASSIC TEMPLATE ====================
    private fun renderClassic(
        canvas: Canvas,
        photo: Bitmap?,
        product: Product,
        card: StoryCard,
        artisan: Artisan
    ) {
        // Background
        canvas.drawColor(Color.parseColor("#FFF8F0"))

        // Photo section
        val photoHeight = 620
        photo?.let {
            val cropped = ImageUtils.centerCrop(it, CARD_WIDTH, photoHeight)
            canvas.drawBitmap(cropped, 0f, 0f, null)
        } ?: run {
            val bgPaint = Paint().apply { color = Color.parseColor("#F5E6D3") }
            canvas.drawRect(0f, 0f, CARD_WIDTH.toFloat(), photoHeight.toFloat(), bgPaint)
        }

        // Gradient overlay on photo
        val gradientPaint = Paint()
        gradientPaint.shader = LinearGradient(
            0f, (photoHeight - 250).toFloat(), 0f, photoHeight.toFloat(),
            Color.TRANSPARENT,
            Color.parseColor("#CC5D3A1A"),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, (photoHeight - 250).toFloat(), CARD_WIDTH.toFloat(), photoHeight.toFloat(), gradientPaint)

        // Border frame
        val borderPaint = Paint().apply {
            color = Color.parseColor("#C75B39")
            style = Paint.Style.STROKE
            strokeWidth = 12f
        }
        canvas.drawRect(20f, 20f, (CARD_WIDTH - 20).toFloat(), (CARD_HEIGHT - 20).toFloat(), borderPaint)

        // Inner border
        val innerBorderPaint = Paint().apply {
            color = Color.parseColor("#D4A646")
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawRect(36f, 36f, (CARD_WIDTH - 36).toFloat(), (CARD_HEIGHT - 36).toFloat(), innerBorderPaint)

        // Dynamic text section — starts below photo
        val margin = 60f
        val textMaxWidth = CARD_WIDTH - (margin * 2).toInt()
        var currentY = photoHeight.toFloat() + 30f

        // Product name
        val namePaint = TextPaint().apply {
            color = Color.parseColor("#3E2723")
            textSize = getAdaptiveTextSize(product.name, 48f, textMaxWidth)
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            isAntiAlias = true
        }
        val nameHeight = drawMultiLineText(canvas, product.name, namePaint, margin, currentY, textMaxWidth, 2)
        currentY += nameHeight + 20f

        // Benefit text
        val benefitPaint = TextPaint().apply {
            color = Color.parseColor("#5D3A1A")
            textSize = 32f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        val benefitText = truncateText(card.benefitText, 180)
        val benefitHeight = drawMultiLineText(canvas, benefitText, benefitPaint, margin, currentY, textMaxWidth, 4)
        currentY += benefitHeight + 24f

        // Health fact badge
        val badgeHeight = drawHealthBadge(canvas, card.healthFact, margin, currentY, Color.parseColor("#C75B39"), Color.WHITE)
        currentY += badgeHeight + 30f

        // Artisan info — positioned from bottom for consistency
        val artisanY = (CARD_HEIGHT - 90).toFloat()
        val artisanPaint = TextPaint().apply {
            color = Color.parseColor("#5D3A1A")
            textSize = 28f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        val artisanText = "Crafted by ${artisan.name}"
        canvas.drawText(artisanText, margin, artisanY, artisanPaint)

        val contactPaint = TextPaint().apply {
            color = Color.parseColor("#8D6E63")
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        canvas.drawText("📞 ${artisan.phone}", margin, artisanY + 32f, contactPaint)

        // Watermark
        drawWatermark(canvas, Color.parseColor("#805D3A1A"))
    }

    // ==================== MODERN TEMPLATE ====================
    private fun renderModern(
        canvas: Canvas,
        photo: Bitmap?,
        product: Product,
        card: StoryCard,
        artisan: Artisan
    ) {
        canvas.drawColor(Color.WHITE)

        val photoHeight = 580
        // Photo with padding
        photo?.let {
            val cropped = ImageUtils.centerCrop(it, CARD_WIDTH - 80, photoHeight)
            canvas.drawBitmap(cropped, 40f, 40f, null)
        } ?: run {
            val bgPaint = Paint().apply { color = Color.parseColor("#F5F5F5") }
            canvas.drawRoundRect(RectF(40f, 40f, (CARD_WIDTH - 40).toFloat(), (40 + photoHeight).toFloat()), 16f, 16f, bgPaint)
        }

        // Subtle gradient
        val gradientPaint = Paint()
        gradientPaint.shader = LinearGradient(
            0f, (photoHeight - 100).toFloat(), 0f, (40 + photoHeight).toFloat(),
            Color.TRANSPARENT,
            Color.parseColor("#40000000"),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(40f, (photoHeight - 100).toFloat(), (CARD_WIDTH - 40).toFloat(), (40 + photoHeight).toFloat(), gradientPaint)

        // Dynamic text section
        val margin = 60f
        val textMaxWidth = CARD_WIDTH - (margin * 2).toInt()
        var currentY = (40 + photoHeight + 40).toFloat()

        // Accent line
        val accentPaint = Paint().apply {
            color = Color.parseColor("#C75B39")
            strokeWidth = 4f
        }
        canvas.drawLine(margin, currentY, (CARD_WIDTH - margin), currentY, accentPaint)
        currentY += 30f

        // Product name
        val namePaint = TextPaint().apply {
            color = Color.parseColor("#1A1A1A")
            textSize = getAdaptiveTextSize(product.name, 44f, textMaxWidth)
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
        }
        val nameHeight = drawMultiLineText(canvas, product.name, namePaint, margin, currentY, textMaxWidth, 2)
        currentY += nameHeight + 12f

        // Category tag
        val categoryPaint = TextPaint().apply {
            color = Color.parseColor("#999999")
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
            letterSpacing = 0.15f
        }
        canvas.drawText(product.category.uppercase(), margin, currentY + 20f, categoryPaint)
        currentY += 40f

        // Benefit text
        val benefitPaint = TextPaint().apply {
            color = Color.parseColor("#444444")
            textSize = 30f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
            letterSpacing = 0.02f
        }
        val benefitText = truncateText(card.benefitText, 180)
        val benefitHeight = drawMultiLineText(canvas, benefitText, benefitPaint, margin, currentY, textMaxWidth, 4)
        currentY += benefitHeight + 24f

        // Health fact
        val badgeHeight = drawHealthBadge(canvas, card.healthFact, margin, currentY, Color.parseColor("#1A1A1A"), Color.WHITE)
        currentY += badgeHeight + 20f

        // Artisan info — from bottom
        val artisanY = (CARD_HEIGHT - 80).toFloat()
        val artisanPaint = TextPaint().apply {
            color = Color.parseColor("#999999")
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        canvas.drawText("${artisan.name} · ${artisan.phone}", margin, artisanY, artisanPaint)

        drawWatermark(canvas, Color.parseColor("#40CCCCCC"))
    }

    // ==================== HERITAGE TEMPLATE ====================
    private fun renderHeritage(
        canvas: Canvas,
        photo: Bitmap?,
        product: Product,
        card: StoryCard,
        artisan: Artisan
    ) {
        canvas.drawColor(Color.parseColor("#FFF5E1"))

        val photoHeight = 580
        photo?.let {
            val cropped = ImageUtils.centerCrop(it, CARD_WIDTH - 100, photoHeight)
            canvas.drawBitmap(cropped, 50f, 80f, null)
        } ?: run {
            val bgPaint = Paint().apply { color = Color.parseColor("#F5E6D3") }
            canvas.drawRoundRect(RectF(50f, 80f, (CARD_WIDTH - 50).toFloat(), (80 + photoHeight).toFloat()), 12f, 12f, bgPaint)
        }

        // Gold gradient overlay
        val gradientPaint = Paint()
        gradientPaint.shader = LinearGradient(
            0f, (photoHeight - 100).toFloat(), 0f, (80 + photoHeight).toFloat(),
            Color.TRANSPARENT,
            Color.parseColor("#AAD4A646"),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(50f, (photoHeight - 100).toFloat(), (CARD_WIDTH - 50).toFloat(), (80 + photoHeight).toFloat(), gradientPaint)

        // Gold border
        val borderPaint = Paint().apply {
            color = Color.parseColor("#D4A646")
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }
        canvas.drawRect(30f, 30f, (CARD_WIDTH - 30).toFloat(), (CARD_HEIGHT - 30).toFloat(), borderPaint)

        drawCornerDecor(canvas, Color.parseColor("#D4A646"))

        // Dynamic text
        val margin = 70f
        val textMaxWidth = CARD_WIDTH - (margin * 2).toInt()
        var currentY = (80 + photoHeight + 30).toFloat()

        // Product name
        val namePaint = TextPaint().apply {
            color = Color.parseColor("#5D3A1A")
            textSize = getAdaptiveTextSize(product.name, 46f, textMaxWidth)
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            isAntiAlias = true
        }
        val nameHeight = drawMultiLineText(canvas, product.name, namePaint, margin, currentY, textMaxWidth, 2)
        currentY += nameHeight + 16f

        // Ornamental divider
        val dividerPaint = Paint().apply {
            color = Color.parseColor("#D4A646")
            strokeWidth = 3f
        }
        canvas.drawLine(200f, currentY, (CARD_WIDTH - 200).toFloat(), currentY, dividerPaint)
        // Diamond
        val cx = CARD_WIDTH / 2f
        val path = android.graphics.Path().apply {
            moveTo(cx, currentY - 10f)
            lineTo(cx + 10f, currentY)
            lineTo(cx, currentY + 10f)
            lineTo(cx - 10f, currentY)
            close()
        }
        val diamondPaint = Paint().apply { color = Color.parseColor("#D4A646"); style = Paint.Style.FILL }
        canvas.drawPath(path, diamondPaint)
        currentY += 28f

        // Benefit text (quoted)
        val benefitPaint = TextPaint().apply {
            color = Color.parseColor("#3E2723")
            textSize = 30f
            typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
            isAntiAlias = true
        }
        val quotedBenefit = "\"${truncateText(card.benefitText, 160)}\""
        val benefitHeight = drawMultiLineText(canvas, quotedBenefit, benefitPaint, margin, currentY, textMaxWidth, 4)
        currentY += benefitHeight + 22f

        // Health fact
        val badgeHeight = drawHealthBadge(canvas, card.healthFact, margin, currentY, Color.parseColor("#D4A646"), Color.parseColor("#5D3A1A"))
        currentY += badgeHeight + 20f

        // Artisan info — from bottom
        val artisanY = (CARD_HEIGHT - 100).toFloat()
        val artisanPaint = TextPaint().apply {
            color = Color.parseColor("#5D3A1A")
            textSize = 26f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        canvas.drawText("✦ Crafted by ${artisan.name} ✦", margin, artisanY, artisanPaint)
        canvas.drawText("📞 ${artisan.phone}", margin, artisanY + 30f, artisanPaint)

        drawWatermark(canvas, Color.parseColor("#60D4A646"))
    }

    // ==================== ECO TEMPLATE ====================
    private fun renderEco(
        canvas: Canvas,
        photo: Bitmap?,
        product: Product,
        card: StoryCard,
        artisan: Artisan
    ) {
        canvas.drawColor(Color.parseColor("#F0F8F0"))

        val photoHeight = 600
        photo?.let {
            val cropped = ImageUtils.centerCrop(it, CARD_WIDTH - 60, photoHeight)
            canvas.drawBitmap(cropped, 30f, 30f, null)
        } ?: run {
            val bgPaint = Paint().apply { color = Color.parseColor("#E8F5E9") }
            canvas.drawRoundRect(RectF(30f, 30f, (CARD_WIDTH - 30).toFloat(), (30 + photoHeight).toFloat()), 20f, 20f, bgPaint)
        }

        // Green gradient overlay
        val gradientPaint = Paint()
        gradientPaint.shader = LinearGradient(
            0f, (photoHeight - 200).toFloat(), 0f, (30 + photoHeight).toFloat(),
            Color.TRANSPARENT,
            Color.parseColor("#CC2E7D32"),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(30f, (photoHeight - 200).toFloat(), (CARD_WIDTH - 30).toFloat(), (30 + photoHeight).toFloat(), gradientPaint)

        // Eco badge on photo
        val ecoBadgePaint = Paint().apply {
            color = Color.parseColor("#4CAF50")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(60f, 60f, 220f, 108f), 24f, 24f, ecoBadgePaint)
        val ecoText = TextPaint().apply {
            color = Color.WHITE
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
        }
        canvas.drawText("🌿 ECO", 80f, 93f, ecoText)

        // Dynamic text
        val margin = 50f
        val textMaxWidth = CARD_WIDTH - (margin * 2).toInt()
        var currentY = (30 + photoHeight + 30).toFloat()

        // Green accent line
        val linePaint = Paint().apply {
            color = Color.parseColor("#4CAF50")
            strokeWidth = 6f
            strokeCap = Paint.Cap.ROUND
        }
        canvas.drawLine(margin, currentY, margin + 200f, currentY, linePaint)
        currentY += 24f

        // Product name
        val namePaint = TextPaint().apply {
            color = Color.parseColor("#1B5E20")
            textSize = getAdaptiveTextSize(product.name, 44f, textMaxWidth)
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
        }
        val nameHeight = drawMultiLineText(canvas, product.name, namePaint, margin, currentY, textMaxWidth, 2)
        currentY += nameHeight + 16f

        // Benefit text
        val benefitPaint = TextPaint().apply {
            color = Color.parseColor("#2E7D32")
            textSize = 30f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        val benefitText = truncateText(card.benefitText, 180)
        val benefitHeight = drawMultiLineText(canvas, benefitText, benefitPaint, margin, currentY, textMaxWidth, 4)
        currentY += benefitHeight + 22f

        // Health fact
        val badgeHeight = drawHealthBadge(canvas, card.healthFact, margin, currentY, Color.parseColor("#2E7D32"), Color.WHITE)
        currentY += badgeHeight + 20f

        // Sustainability note
        val notePaint = TextPaint().apply {
            color = Color.parseColor("#66BB6A")
            textSize = 22f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC)
            isAntiAlias = true
        }
        canvas.drawText("100% Natural Clay · Eco-Friendly · Handcrafted", margin, currentY + 18f, notePaint)

        // Artisan info — from bottom
        val artisanY = (CARD_HEIGHT - 80).toFloat()
        val artisanPaint = TextPaint().apply {
            color = Color.parseColor("#2E7D32")
            textSize = 26f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }
        canvas.drawText("Crafted by ${artisan.name} | 📞 ${artisan.phone}", margin, artisanY, artisanPaint)

        drawWatermark(canvas, Color.parseColor("#604CAF50"))
    }

    // ==================== SHARED HELPERS ====================

    /**
     * Calculate adaptive text size to prevent overflow.
     * Reduces font size if text is too long for one line.
     */
    private fun getAdaptiveTextSize(text: String, maxSize: Float, maxWidth: Int): Float {
        var size = maxSize
        val paint = TextPaint().apply {
            textSize = size
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }
        // If text fits in ~1.5 lines at max size, keep it. Otherwise reduce.
        while (size > 28f) {
            paint.textSize = size
            val textWidth = paint.measureText(text)
            if (textWidth <= maxWidth * 1.8f) break
            size -= 2f
        }
        return size
    }

    /**
     * Truncate text to a maximum character length, adding "..." if needed.
     */
    private fun truncateText(text: String, maxChars: Int): String {
        return if (text.length > maxChars) {
            text.take(maxChars - 3).trimEnd() + "..."
        } else text
    }

    /**
     * Draw multi-line text with proper wrapping using StaticLayout.
     * Returns the total height consumed by the text.
     */
    private fun drawMultiLineText(
        canvas: Canvas,
        text: String,
        paint: TextPaint,
        x: Float,
        y: Float,
        maxWidth: Int,
        maxLines: Int = 4
    ): Int {
        if (text.isEmpty()) return 0

        canvas.save()
        canvas.translate(x, y)

        val layout = StaticLayout.Builder
            .obtain(text, 0, text.length, paint, maxWidth)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(6f, 1.15f)
            .setIncludePad(false)
            .setMaxLines(maxLines)
            .build()

        layout.draw(canvas)
        canvas.restore()

        return layout.height
    }

    /**
     * Draw a health fact badge/chip.
     * Returns the total height consumed.
     */
    private fun drawHealthBadge(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        bgColor: Int,
        textColor: Int
    ): Int {
        if (text.isEmpty()) return 0

        val displayText = "🏥 $text"
        val textPaint = TextPaint().apply {
            color = textColor
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
        }

        val textWidth = textPaint.measureText(displayText)
        val padding = 20f
        val badgeHeight = 46f

        // Badge background
        val bgPaint = Paint().apply {
            color = bgColor
            isAntiAlias = true
        }
        canvas.drawRoundRect(
            RectF(x, y, x + textWidth + padding * 2, y + badgeHeight),
            badgeHeight / 2,
            badgeHeight / 2,
            bgPaint
        )

        // Badge text
        canvas.drawText(displayText, x + padding, y + 30f, textPaint)

        return badgeHeight.toInt()
    }

    /**
     * Draw corner decorations for Heritage template.
     */
    private fun drawCornerDecor(canvas: Canvas, color: Int) {
        val paint = Paint().apply {
            this.color = color
            style = Paint.Style.STROKE
            strokeWidth = 3f
            isAntiAlias = true
        }

        val size = 60f
        val offset = 45f

        // Top-left
        canvas.drawArc(RectF(offset, offset, offset + size, offset + size), 180f, 90f, false, paint)
        canvas.drawArc(RectF(offset + 15, offset + 15, offset + size - 15, offset + size - 15), 180f, 90f, false, paint)

        // Top-right
        val right = CARD_WIDTH - offset
        canvas.drawArc(RectF(right - size, offset, right, offset + size), 270f, 90f, false, paint)
        canvas.drawArc(RectF(right - size + 15, offset + 15, right - 15, offset + size - 15), 270f, 90f, false, paint)

        // Bottom-left
        val bottom = CARD_HEIGHT - offset
        canvas.drawArc(RectF(offset, bottom - size, offset + size, bottom), 90f, 90f, false, paint)
        canvas.drawArc(RectF(offset + 15, bottom - size + 15, offset + size - 15, bottom - 15), 90f, 90f, false, paint)

        // Bottom-right
        canvas.drawArc(RectF(right - size, bottom - size, right, bottom), 0f, 90f, false, paint)
        canvas.drawArc(RectF(right - size + 15, bottom - size + 15, right - 15, bottom - 15), 0f, 90f, false, paint)
    }

    /**
     * Draw Kumbara-Kala watermark in bottom-right corner.
     */
    private fun drawWatermark(canvas: Canvas, color: Int) {
        val paint = TextPaint().apply {
            this.color = color
            textSize = 22f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.RIGHT
        }
        canvas.drawText("Kumbara-Kala", (CARD_WIDTH - 50).toFloat(), (CARD_HEIGHT - 50).toFloat(), paint)
    }
}
