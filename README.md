# Maze-Similarity
![Alt Text](https://raw.githubusercontent.com/thedtripp/Maze-Similarity/main/assets/aerial-hedge-maze.webp)
Given a series of mazes encoded as zeros and ones, it was determined which two of the mazes were least similar. To solve, the mazes were converted into graphs and traversed using a depth first search, resulting in a sequence of directions. Sequence similarity was analyzed by evaluating each pair of sequences using a dynamic programming algorithm to find the maximum common subsequence. Of these results, the two mazes with the lowest similarity score were chosen.

## IMG
<img align=left src="https://github.com/thedtripp/Maze-Similarity/blob/main/assets/Maze-visualization.png" width=360 height=380 alt="">
<img align=right src="https://github.com/thedtripp/Maze-Similarity/blob/main/assets/Maze-n4-grid.png" width=530 height=380 alt="">




