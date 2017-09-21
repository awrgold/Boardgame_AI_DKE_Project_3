# Project-2.1-v2
The new repository for Project 2.1, with updated gitignore.

*Removed android/html modules, as they were unnecessary*

Notes:

- Keep all classes inside \core\src\com.game, though feel free to create subfolders within.
- Please make sure that every commit has a *detailed* message describing what you've done. 
- We will most likely have 2-3 branches at a time: GUI and Logic (phase 1) and AI (phases 2-3).
- We should spend the first moments of every Thursday meeting merging our branches, making sure everyone's code is up to date, 
and that we can rebranch safely and continue forward. 
- If you're having issues, DON'T PUSH and please contact the team for advice.
- Project material is due 19 October, where we will also have a presentation. 

# Phase 1 Goals:

- 1v1 human players in turn-based game, where the hand of each player appears during their turn and disappears at the end of the turn.
- #We need:
    - Main menu
    - Playing board
        - Random color in each corner at start of game
        - Game represented as graph in back end
    - 6 Hex tiles in hand
        - 120 (?) tiles in "bag"
    - Scoreboard
        - highlight the lowest color score?
    - Rotation ability with game tiles
    - Additional turn if >= 18 points are scored
    - Ability to discard hand and draw new tiles if no possible moves
- We may want:
    - ability to parse game states and "save/load" games
    - ???
