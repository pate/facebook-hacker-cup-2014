# Facebook Hacker Cup 2014

This is my first coding competition attempted in Clojure. I finished the first two out of three problems, Square Detector and Basketball Game.

## How to Run
You need Leiningen to run these Clojure projects. Both projects accept piped input to `stdin` and output to `stdout`. To run:

> cd squares
> lein run

To pass in data on Windows;
 
> type .\example-in.txt | lein run

And to output to a file:

> type .\example-in.txt | lein run > example-out.txt

## Solutions
### Square Detector

My square detector works by parsing the input grid of periods and hashes as a matrix of 1's and 0's, e.g. `[(0 0 1 1) (0 0 1 1) (0 0 0 0)]`. I then survey the landscape by summing the values of every row and every column into two "survey" vectors.

For a square to exist, there should occur exactly one contiguous sequence 1's of equal length in both survey vectors. This is determined by partitioning the survey vectors into their run-lengths and throwing away any leading or trailing sequences of zeroes. The partitioned vectors are then checked for one contiguous sequence of equal length.

Any outlying hashes or gaps in the center of the square will result in another sequence that gets partitioned, or will ruin the run-length.

### Basketball Game

Straight-forward recursive solution that counts down the minutes and swaps out players every minute until the time runs out. Most of the work is in parsing and juggling data.
