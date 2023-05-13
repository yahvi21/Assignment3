# Assignment3

Friends In Scandal 

Using graphs, graph algorithms and object oriented design by handling a large dataset efficiently.

OBJECTIVE: The code provided is for a program that analyzes email data to provide statistics about email usage by specific individuals. The program takes a path to a directory containing email data files and allows the user to input an email address for analysis. It then outputs the number of emails sent by the user, the number of emails received by the user, and the number of people the user is on a team with. 

ABOUT MY EXECUTION: - My project runs and compiles without any error. -Extensive comments are provided in the code to understand each methods and its functionality.

In my code I am getting total 517512 count for total number of valid files. The number is higher than given in the document and I believe it is becuase my code reads sub folders till a file is read. This is because when I was going throough the data set, I noticed that some sub folders of induviduals had more subfolders even when most of the just had numbered files. Hence I implmented a code to read folder until file is found. 

And for my count of valid emails in my code I have tested and got 3 types of valid outputs. 
- My count after skipping emails if the recievers's email address does not end with "@enron.com" is : 16813
- My count after skipping emails if the sender's email address does not end with "@enron.com" is : 6341
- My count after skipping emails if the sender's email address does not end with "@enron.com" and recievers's email address does not end with "@enron.com" is : 6288
The code to check these counts are commented. Uncomment to check.

I am getting the correct count for emails sent and recieved of an induvidual, when input a particular email. I believe I have successfully implemented requirement 1 and 2. 

For Requirement 3. My DFS is not implementing correctly. I have commented my progress of all I could figure out how to do that part.

It takes about 2 minutes for reading all the dataset when I found the count of valid emails.
