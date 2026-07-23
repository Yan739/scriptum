---
name: Scriptum Design System
colors:
  surface: '#fcf8ff'
  surface-dim: '#dcd9e0'
  surface-bright: '#fcf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f6f2fa'
  surface-container: '#f0ecf4'
  surface-container-high: '#eae7ef'
  surface-container-highest: '#e5e1e9'
  on-surface: '#1b1b21'
  on-surface-variant: '#474651'
  inverse-surface: '#303036'
  inverse-on-surface: '#f3eff7'
  outline: '#777682'
  outline-variant: '#c8c5d3'
  surface-tint: '#5654a8'
  primary: '#1a146b'
  on-primary: '#ffffff'
  primary-container: '#312e81'
  on-primary-container: '#9c9af4'
  inverse-primary: '#c3c0ff'
  secondary: '#0051d5'
  on-secondary: '#ffffff'
  secondary-container: '#316bf3'
  on-secondary-container: '#fefcff'
  tertiary: '#3e1a00'
  on-tertiary: '#ffffff'
  tertiary-container: '#5f2b00'
  on-tertiary-container: '#de915e'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#e2dfff'
  primary-fixed-dim: '#c3c0ff'
  on-primary-fixed: '#100563'
  on-primary-fixed-variant: '#3e3c8f'
  secondary-fixed: '#dbe1ff'
  secondary-fixed-dim: '#b4c5ff'
  on-secondary-fixed: '#00174b'
  on-secondary-fixed-variant: '#003ea8'
  tertiary-fixed: '#ffdbc7'
  tertiary-fixed-dim: '#ffb688'
  on-tertiary-fixed: '#311300'
  on-tertiary-fixed-variant: '#70380b'
  background: '#fcf8ff'
  on-background: '#1b1b21'
  surface-variant: '#e5e1e9'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 48px
    fontWeight: '700'
    lineHeight: 56px
    letterSpacing: -0.02em
  display-lg-mobile:
    fontFamily: Inter
    fontSize: 36px
    fontWeight: '700'
    lineHeight: 44px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-sm:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  2xl: 48px
  3xl: 64px
  gutter: 24px
  margin-mobile: 16px
  margin-desktop: 40px
---

## Brand & Style
The design system embodies a premium, high-performance aesthetic tailored for an AI-powered transcription platform. The personality is professional yet visionary, blending the precision of developer-centric tools with the approachability of high-end consumer hardware.

The design style is **Minimalist-Glassmorphic**. It leverages expansive whitespace, a disciplined color palette, and subtle translucent layers to create a sense of depth and clarity. Influenced by modern SaaS leaders, the interface remains functional and "quiet," allowing the user’s content—the transcriptions—to take center stage while AI-driven features are highlighted through sophisticated light-play and gradients.

## Colors
The palette is rooted in deep, authoritative blues and indigos to establish trust and technological prowess. 

- **Primary & Secondary:** Deep Indigo and Royal Blue are used for core branding, primary actions, and active states.
- **Accent:** Cyan is reserved for AI-assisted features, highlights, and subtle progress indicators to signify innovation.
- **Backgrounds:** A crisp White is used for content surfaces, while Light Gray (#F8FAFC) provides a soft contrast for structural backgrounds and sidebar navigation.
- **Semantic:** Emerald (Success), Rose (Error), and Sky (Info) are utilized sparingly for status communication, maintaining high legibility against light backgrounds.

## Typography
The system utilizes **Inter** for its exceptional legibility and systematic feel. The type hierarchy is defined by tight letter-spacing in larger headings to create a "custom-font" premium appearance, while body text maintains standard tracking for optimal reading of long-form transcripts.

Weights are used purposefully: **Bold (700)** for displays, **Semi-Bold (600)** for structural headings, and **Medium (500)** for interactive labels. Use `label-sm` with slight uppercase tracking for metadata and non-interactive UI markers.

## Layout & Spacing
The system uses an **8px linear grid** to ensure mathematical harmony across all components. 

- **Grid Model:** A 12-column fluid grid for desktop with 24px gutters. On mobile, transition to a single-column layout with 16px side margins.
- **Rhythm:** Vertical rhythm should follow the 8px scale. Component internal padding should generally be 16px (md) or 24px (lg) to maintain the "spacious" premium feel.
- **Alignment:** Content is centered in a max-width container of 1280px for standard dashboards, while the transcription editor may use a narrower 800px focused container to reduce eye strain.

## Elevation & Depth
This design system uses **Glassmorphism** and **Ambient Shadows** to define hierarchy.

- **Level 1 (Base):** Light Gray (#F8FAFC) background.
- **Level 2 (Cards):** White background with a 1px border (#E2E8F0) and a very soft, diffused shadow (0 4px 6px -1px rgba(0,0,0,0.05)).
- **Level 3 (Overlays/Glass):** Surfaces use a background blur (12px to 20px) with a semi-transparent white fill (rgba(255, 255, 255, 0.7)). A 1px translucent border provides a "sharp" edge.
- **Level 4 (Modals):** High-elevation shadows with a larger blur radius (20px-40px) and a subtle Indigo tint in the shadow (rgba(49, 46, 129, 0.08)).

## Shapes
The shape language is sophisticated and approachable, characterized by generous corner radii.

- **Standard Elements:** 16px (rounded-lg) is the default for most cards and containers.
- **Outer Shells:** Main dashboard containers use 24px (rounded-xl) to create a soft, framed appearance.
- **Small Elements:** Buttons and inputs use 8px (rounded) to maintain a crisp, professional edge while still feeling soft to the touch.

## Components
- **Buttons:** Primary buttons use a subtle vertical gradient from Royal Blue to Deep Indigo. Secondary buttons are ghost-style with a 1px border. All buttons have a 200ms transition on hover with a slight scale-up (1.02x).
- **Glass Cards:** Used for AI insights and "Now Playing" widgets. Use backdrop-filter: blur(16px) with a subtle Cyan-tinted inner glow.
- **Input Fields:** Minimalist style. No background, just a bottom border in the resting state, transitioning to a full 1px Indigo border with a soft focus ring on active.
- **Loading Skeletons:** Use a subtle shimmering gradient that moves from Light Gray to a slightly cooler blue-tinted gray, reflecting the AI-processing nature of the platform.
- **Lists:** Transcription entries should be separated by whitespace and soft shadows rather than hard dividers, creating a "floating" feel.
- **Icons:** Use Lucide-style icons with a consistent 2px stroke width. Use Cyan for AI-related icons and Deep Indigo for utility icons.