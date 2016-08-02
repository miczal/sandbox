SushiGoRoundBot
===============

Bot for Sushi Go Round flash game. Version which can get you to 5th level.

Problems: 
-Specific for my screen resolution and browser (change originX/Y and screenGrabber coordinates). 
-Has problems with first delivery if ingredients are expensive (waiting period should be longer in that case - we are waiting for money from big order) 
-Sometimes it recognizes sushi to order incorrectly - probably compression issues in screenGrabber 
-BEFORE refactoring 

Remarks: 
-Idea based on python tutorial
-It uses one thread per customer. It seemed more comfortable 
-Didn't test how it runs without debug in console - should be much smoother 
-Score of committed version (around this values, depends on a game): 
 Level 1 - 1860 Level 2 - 3460 Level 3 - 4230 Level 4 - 6080

What is this repository for?
===============

My project for getting hang of Java syntax. Unfortunately doesn't have any UTs.

How do I get set up?
===============
Download
Run Sushi Go Round in your favourite browser.
Take a screenshot and see where TOP LEFT corner of the play area is (where two black lines cross). Write its coordinates to ScreenTrimmer class (in ScreenGrabber.java) and SushiMaker.java - originX originY.
Start level and start bot. After level has finished bot has to be closed and restarted - it doesn't keep track of ingredients throughout few levels, because I didn't implement finished level recognition.

Who do I talk to?
===============
If somebody has questions about this repo - contact me: mpierscinski@gmail.com
