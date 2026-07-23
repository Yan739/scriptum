---
name: Scriptum Antiqua
colors:
  surface: '#fff8f0'
  surface-dim: '#e3d9c6'
  surface-bright: '#fff8f0'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#fdf3df'
  surface-container: '#f7edda'
  surface-container-high: '#f1e7d4'
  surface-container-highest: '#ebe1cf'
  on-surface: '#1f1b10'
  on-surface-variant: '#51443a'
  inverse-surface: '#353023'
  inverse-on-surface: '#faf0dd'
  outline: '#847469'
  outline-variant: '#d6c3b6'
  surface-tint: '#855324'
  primary: '#552c00'
  on-primary: '#ffffff'
  primary-container: '#704214'
  on-primary-container: '#f1b179'
  inverse-primary: '#fbb980'
  secondary: '#775928'
  on-secondary: '#ffffff'
  secondary-container: '#ffd79b'
  on-secondary-container: '#7a5c2b'
  tertiary: '#373636'
  on-tertiary: '#ffffff'
  tertiary-container: '#4e4d4d'
  on-tertiary-container: '#c0bebd'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdcc1'
  primary-fixed-dim: '#fbb980'
  on-primary-fixed: '#2e1500'
  on-primary-fixed-variant: '#693c0e'
  secondary-fixed: '#ffdeae'
  secondary-fixed-dim: '#e8c086'
  on-secondary-fixed: '#281800'
  on-secondary-fixed-variant: '#5d4213'
  tertiary-fixed: '#e5e2e1'
  tertiary-fixed-dim: '#c8c6c5'
  on-tertiary-fixed: '#1b1b1c'
  on-tertiary-fixed-variant: '#474746'
  background: '#fff8f0'
  on-background: '#1f1b10'
  surface-variant: '#ebe1cf'
typography:
  display-lg:
    fontFamily: EB Garamond
    fontSize: 48px
    fontWeight: '600'
    lineHeight: 56px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: EB Garamond
    fontSize: 32px
    fontWeight: '500'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: EB Garamond
    fontSize: 28px
    fontWeight: '500'
    lineHeight: 36px
  body-md:
    fontFamily: Courier Prime
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-sm:
    fontFamily: Courier Prime
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: JetBrains Mono
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  margin-page: 4rem
  gutter: 1.5rem
  sheet-overlap: -2rem
  stack-offset: 4px
---

## Brand & Style

The design system is rooted in the tactile heritage of literary creation, blending the scholarly elegance of ancient manuscripts with the mechanical precision of mid-century typewriters. It targets writers, historians, and bibliophiles who seek a focused, analog-inspired digital environment that evokes the smell of old paper and the rhythmic clack of keys.

The design style is **Tactile / Skeuomorphic**, emphasizing physical metaphors. UI elements are treated as physical objects: windows are parchment sheets, buttons are molded resin or brass keys, and containers are leather-bound folios. The emotional response is one of timelessness, intellectual depth, and rhythmic focus. It eschews modern transparency and vibrant digital glow in favor of organic textures, ink-bleed effects, and the warmth of a wooden writing desk.

## Colors

The palette is derived from natural materials and historical artifacts. 
- **Primary (Sepia):** Used for focused states, ink signatures, and primary branding elements.
- **Secondary (Antique Gold):** Reserved for highlights, ornate borders, and "brass" hardware accents.
- **Background (Aged Paper):** The foundational canvas for all views, suggesting a continuous rolls of parchment or a solid wooden desk surface.
- **Surface (Ivory):** Used for active writing areas and "stacked" sheets to provide subtle contrast against the background.
- **Text (Dark Brown):** Replaces pure black to maintain a softer, organic feel reminiscent of walnut ink.
- **Utility (Matte Black):** Specifically for interactive "keys" and mechanical UI components.

## Typography

Typography is the core of this design system, representing the bridge between handwritten history and mechanical modernism.

- **Headlines:** Utilize **EB Garamond** (a digital relative of Cormorant) for an authoritative, literary feel. It should be used for titles, chapter headings, and large quotes.
- **Body:** **Courier Prime** is the primary choice for all long-form content, mimicking the fixed-width output of a classic typewriter. It provides a rhythmic, distraction-free reading experience.
- **Labels/Technical:** **JetBrains Mono** is used for small metadata, button labels, and instructional text to suggest the internal "mechanics" of the application while maintaining legibility.

All text should have a subtle "ink bleed" effect (low-opacity text-shadow) to prevent it from looking too digitally perfect.

## Layout & Spacing

The layout follows a **Fixed Grid** philosophy, centered on the screen like a sheet of paper on a desk or a book on a lectern. 

- **Desktop:** A wide central column (800px max) mimics the width of a standard A4 or Letter page. Margins are generous to simulate the "workspace" around the document.
- **The Stack:** Elements are often layered using "stack offsets," where sheets of ivory paper appear to be piled on top of each other.
- **Mobile:** The margins compress, but the "sheet" metaphor remains. The page edges should be visible to maintain the sense of depth.
- **Rhythm:** Spacing is inspired by typesetting. Paragraph gaps match the line-height of the body text to maintain a consistent vertical "striping" effect.

## Elevation & Depth

Hierarchy is established through **Physical Layering** and **Realistic Shadows**.

1.  **Level 0 (The Desk):** The main background using the Aged Paper texture. No shadow.
2.  **Level 1 (The Sheet):** Ivory-colored containers with a soft, multi-layered "drop shadow" that suggests the thickness of heavy cardstock (e.g., `0 4px 12px rgba(59, 47, 47, 0.1)`).
3.  **Level 2 (The Key):** High-contrast elements like buttons use a dual shadow: a dark bottom shadow for depth and a light top "rim" highlight to simulate a 3D molded edge.
4.  **The Overlap:** Cards should occasionally overlap by a few pixels to reinforce the idea of scattered physical documents.

## Shapes

The shape language varies by material:
- **Paper Sheets:** Use a "Soft" (0.25rem) radius. In some cases, a slight irregular "deckle edge" mask should be applied to simulate handmade paper.
- **Typewriter Keys:** Circular or "squircle" shapes with heavy bezels.
- **Input Slots:** Sharp, rectangular recesses that look like they have been cut into the desk or the machine housing.

## Components

- **Buttons:** Styled as Matte Black typewriter keys. They should have a deep "press" animation where the shadow disappears and the element moves 2px downward. Primary actions can be capped with a brass (Antique Gold) rim.
- **Cards (Sheets):** Ivory surfaces with a subtle parchment texture. Multiple cards can be "stacked" using a repeated border-bottom and box-shadow to look like a ream of paper.
- **Input Fields:** Styled as "Paper Slots." They appear as a horizontal slit in the UI; as the user types, the text appears to emerge from the slot onto an ivory background.
- **Chips/Tags:** Styled as small manila luggage tags or library card tabs, using the Antique Gold color for the "string" or attachment point.
- **Lists:** Items are separated by subtle "ink-stamped" lines or hand-drawn dividers.
- **Scrollbars:** Custom-styled as a wooden or brass slider on the right edge of the "desk."