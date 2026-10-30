# Kinnect repaired rubric checklist

Sources: the three-page CS4039 SMD Assignment 1 brief and 23-screen Kinnect_UI.pdf. This supersedes the earlier audit of d093c02. No predicted numerical grade is assigned.

| Criterion | Marks | Current evidence |
|---|---:|---|
| UI accuracy | 30 | All 23 screens implemented as native XML. Reference teal, layouts, rounded cards, photo overlaps, reaction pill, story controls, profile forms, chat/call and marketplace details reviewed against the PDF. Original vector artwork and platform typography remain approximations; pixel equality is not certified. |
| Components | 15 | Individual Activities, ordinary widgets, click listeners and explicit Intents. No advanced architecture. README explains every supporting technique beyond the baseline; only supplied class material/instructor confirmation can determine its syllabus status. |
| Navigation | 15 | Specified destinations and Back flows implemented. Regression tests cover authentication, main-tab history, comments, profiles, picker return/recreation, story Back history, person-specific chat/call and logout task clearing. |
| Accessibility/responsiveness | 5 | Scrollable content, dp/sp, weights, descriptions/hints, safe system/keyboard insets, larger badge touch areas, avatar auto-sizing, adaptive large-font forms, tablet width cap and landscape call layout. Phone portrait/landscape and alternate-size Android XML renders inspected. Physical-tablet and a full TalkBack user study are not claimed. |
| Version control | 10 | Real progressive feature commits pushed, including separate repair milestones for picker, profiles, auth, feed, stories, chat/call, social screens and marketplace. Printed deadline October 4 precedes actual commits. Only instructor acceptance/a revised deadline can resolve that; history has not been backdated. |
| Tests | 5 | Two required Espresso workflows plus ten regression workflows, a phone screenshot test and a configuration-render test. See Validation for final executed result. |
| Live demo | 20 | Student action. DEMO_GUIDE supplies a walkthrough, simple changes and debugging explanations. Automated work cannot perform the assessed explanation on the student's behalf. |

## Repairs by reference screen

| Screen | Work completed after the original audit |
|---|---|
| 01 Splash | Retained timed Login transition, branding and footer; stable configuration rendering. |
| 02 Login | Recent-account shortcut, password visibility, floating field labels, notification badge, language control, outlined CTA and footer. |
| 03 Signup | Profile-photo result, field captions/arrows, three progress segments, birthday defaults, custom radio indicators, password eye/strength and large-font stacked controls. |
| 04 Home | Rounded stories/rings, collage and +4 overlay, reaction artwork, feed actions, published photos and current avatar. |
| 05 Reactions | Consistent custom vectors, rounded pill, raised Love and tooltip; selection updates Home; Share copies post text. |
| 06 Comments | Nested connector lines, overlapping reaction chips, photo, per-comment Like/Reply, target/cancel, sort and persisted new comments. |
| 07 Create post | Rounded action sheet, author controls, palette, selected attachments, local tag/feeling/location/event state and publishing. |
| 08 Picker | Selected-image identities returned, preview/crop, numbered selections, video badge, source tabs, single/multiple modes and recreation restoration. |
| 09 Camera | Rounded preview, vectors and local flash/flip/effects; gallery identity reaches editor. Shutter intentionally uses local illustrated scene. |
| 10 Story editor | Editable caption, local tag/sticker/music/effect/audience state, icon toolbar and sharing while preserving Back history. |
| 11 Story viewer | Segmented progress, correct person/avatar/image, previous/next, reaction state and reply into that person's chat. |
| 12 Your story | Actual shared image/caption/audience, seen-by avatar overlap, hearts and Create/Highlight/Send/More actions. |
| 13 Search | Actual people/category filtering, proper selected profile, group join, recent-search clearing and Marketplace category. |
| 14 Friends | Mutual-avatar overlaps, age labels, 12 local requests, See all, persistent confirm/delete and correct-person profiles. |
| 15 Profile | Cover/avatar/photo-badge overlap, current fields/images, tab actions and friend destinations. |
| 16 Edit profile | Save/reopen all seven fields plus avatar/cover identities; underline form and private section. |
| 17 Other profile | Person-specific name/avatar/details/photos, ring and mutual-avatar overlap, friend/message actions. |
| 18 Notifications | Styled name emphasis, overlapping action badges, read/hide, confirm/delete, expand and correct destinations. |
| 19 Menu | Reference cards/icon colors/chevrons, profile destination, saved/group/event state, expandable shortcuts/settings and logout. |
| 20 Chats | Online/unread/group overlaps, search, correct conversations, last messages, notes and bottom actions. |
| 21 Chat | Correct person, persistent ordered messages/photos, composer actions, quote/reaction/typing composition and call destination. |
| 22 Call | Rounded avatar and halo layers, white vector controls, timer, local mute/speaker/keypad/end and landscape layout. |
| 23 Marketplace | Rounded 2-column cards, badges, profile/search, category/location filters, saved local listings and seller chat. |

## Student responsibilities

1. Supply the required shareable ChatGPT conversation and complete prompts/transcript. AI_ASSISTANCE is explicitly a summary, not a fabricated full export. Retain evidence of the updated AI permission.
2. Confirm instructor acceptance of the actual submission/commit dates and the documented supporting concepts against the class syllabus.
3. Rehearse and perform the assessed live demonstration and submit through the course system.

No connected backend, real camera capture, audio call or transaction service is required by the supplied UI/navigation rubric. Their absence is documented local-demo scope, not a hidden implementation promise. Visual fidelity remains subject to instructor judgment; passing tests are not proof of pixel-perfect equality.
