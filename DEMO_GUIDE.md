# Kinnect live-demo preparation

The student must perform the 20-mark live demonstration. Rehearse with the source open and explain the implementation honestly, including the disclosed AI assistance.

1. Launch splash → Login; try password eye, recent account and signup. Explain setContentView, findViewById, setOnClickListener and explicit Intent.
2. Show Home stories/feed, a reaction, comments/replies and Back. Explain nested XML containers, dp/sp, weights, drawables and overlapping FrameLayouts.
3. Select two photos in Create post, return, publish and show both on Home. Explain the result Intent's selected-image array and local preferences.
4. Follow Camera → editor → Your story; edit caption/use tools. Back must return through editor and camera. Explain the illustrated preview.
5. Search Omar Tariq, open that profile and send a message. Return to Chats and reopen it. Explain person extras and per-person message keys.
6. Edit all seven profile fields and avatar/cover, save and reopen. Explain public versus private profile fields.
7. Confirm/delete a request, expand requests and open notification destinations.
8. Filter Marketplace, add a local listing and open a seller conversation. Explain the local demo scope.
9. Try call mute/speaker/end and rotation. Explain Chronometer and the absence of a real call.
10. Menu → Log out → Back. Home must not reappear; explain NEW_TASK and CLEAR_TASK.

## Small live modifications

- Change a label in res/values/strings.xml, build and demonstrate it.
- Change a layout margin or icon padding, run and explain dp versus sp.
- Change kinnect_teal in colors.xml temporarily and revert; some reference vectors/layouts use the literal reference color, so this demonstrates resources rather than a complete theme switch.
- Add a local option to a Ui.choose array and explain the callback.

## Debugging explanation

For a wrong view ID, compare XML @+id with R.id. For a destination issue, check Activity and manifest. For clipping, inspect parent dimensions, weights, padding and scrolling. Rebuild after source changes and read the first relevant error. Reproduce a workflow failure with its Espresso test and inspect the failing step.

Keep README's supporting-concepts table available. Only class materials/instructor confirmation can establish which techniques were taught. Do not claim real camera capture, account authentication/calling, physical-tablet testing or certified pixel equality.
