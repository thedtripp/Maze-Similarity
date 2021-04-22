# Maze-Similarity
![Alt Text](https://raw.githubusercontent.com/thedtripp/Maze-Similarity/main/assets/aerial-hedge-maze.webp)
Given a series of mazes encoded as zeros and ones, it was determined which two of the mazes were least similar. To solve, the mazes were converted into graphs and traversed using a depth first search, resulting in a sequence of directions. Sequence similarity was analyzed by evaluating each pair of sequences using a dynamic programming algorithm to find the maximum common subsequence. Of these results, the two mazes with the lowest similarity score were chosen.






