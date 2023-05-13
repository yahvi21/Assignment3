import java.util.*;
import java.io.*;

/**
 *
 * @author yahvibhatnagar
 */
class FriendsInScandal {

    // declare a HashMap that maps email addresses to the list of people they sent emails to
    private static HashMap<String, ArrayList<String>> map = new HashMap();
    private static int fileCount = 0;

    public static void main(String[] args) {

        if (args.length < 1 || args.length > 2) {
            System.out.println("Invalid Arguements");
            System.exit(0);
        }

        //path to the directory that contains the email data
        String path = args[0];
        readDataSet(path);
        //System.out.println(fileCount);
        //System.out.println(map.size());
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Email address of the individual (or EXIT to quit): ");
            String email = sc.next();
            if (email.equalsIgnoreCase("EXIT")) {
                break;
            }

            int x = 0, y = 0, z = 0;
            x = getSent(email);
            y = getRecieved(email);
            z = getTeamSize(email);
            if (x == -1) {
                System.out.printf("Email address (%s) not found in the dataset.\n", email);
                continue;
            }
            //Output
            System.out.printf("* %s has sent messages to %d others\n", email, x);
            System.out.printf("* %s has received messages from %d others\n", email, y);
            System.out.printf("* %s is in a team with %d individuals\n", email, z);
            if (args.length == 2) {

                // If an output file is specified in the command-line arguments, write the results to the file
                try {
                    FileWriter writer = new FileWriter(args[1], true);
                    writer.write(email + "-" + z + "\n");
                    writer.close();
                } catch (IOException ex) {

                }

            }
        }
    }

    //If the path points to a file, it calls the 'readFile' method to read the file. 
    //If the path points to a directory, it recursively calls itself on all subdirectories and files. 
    private static void readDataSet(String path) {

        File file = new File(path);

        if (file.isFile()) {
            readFile(path);
            ++fileCount;
        } else {
            // get a list of all the sub directories in the main directory
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                readDataSet(path + "/" + subFile.getName());
            }
        }

    }
    
    //This Method reads files
    private static void readFile(String path) {

        String from = null;
        String to = null;
        File file = new File(path);
        try {
            Scanner reader = new Scanner(file);
            String text = "";
            while (reader.hasNextLine()) {
                text += reader.nextLine();
            }
            reader.close();
            if (text.contains("From: ") && text.contains("To: ") && text.contains("Subject: ")) {
                String x = text;

                // Extract the email address of the sender from the text file.
                int start = text.indexOf("From: ") + 6;
                int end = text.indexOf("To: ");
                from = text.substring(start, end);
                
                // Skip this email if the sender's email address does not end with "@enron.com".            
                /* if (!email.endsWith("@enron.com")) {
                        continue;
                    }
                */
               
                // Extract the email address(es) of the receiver(s) from the text file.
                text = text.substring(end);
                if (!text.startsWith("To: ")) {
                    return;
                }
                if (!text.contains("Subject: ")) {
                    return;
                }
                start = 4;
                end = text.indexOf("Subject: ");
                try {
                    to = text.substring(start, end);
                } catch (Exception e) {
                //System.out.println(x);
                //System.exit(0); 
                }
                
                // Remove any whitespace from the email address(es) of the receiver(s).
                to = to.replaceAll("\\s+", "");
                
                // Split the email address(es) of the receiver(s) by comma and add them to the map.
                String[] to_arr = to.split(",");
                for (String email : to_arr) {
                    
                // Skip this email if the receiver's email address does not end with "@enron.com".                    
                /* if (!email.endsWith("@enron.com")) {
                        continue;
                    }
                */
                    
                    // Add the receiver's email address to the list of recipients for the sender's email address.
                    if (!map.containsKey(from)) {
                        map.put(from, new ArrayList<>());
                    }
                    if (!map.get(from).contains(email)) {
                        map.get(from).add(email);
                    }
                }
            }

        } catch (FileNotFoundException ex) {

        }
    }

    // This method returns the number of emails sent by a specific user.
    // If the user is not found in the map, it returns -1.
    private static int getSent(String user) {
        if (map.containsKey(user)) {
            return map.get(user).size();
        }
        return -1;
    }

    // This method returns the number of emails received by a specific user
    private static int getRecieved(String user) {
        int count = 0;
        for (String sender : map.keySet()) {
            ArrayList<String> recievers = map.get(sender);
            if (recievers.contains(user)) {
                count++;
            }
        }
        return count;
    }
    
    //This method returns team size
    private static int getTeamSize(String user) {
        /* boolean[] visited = new boolean[map.get(user).size()];
        for (int v = 0; v < map.get(user).size(); ++v) {
            if (!visited[v]) {
                // print all reachable vertices
                // from v
                DFS(user, v, visited);
                System.out.println();
            }
        }
         */
        int count = 0;
        for (String sender : map.keySet()) {
            ArrayList<String> recievers = map.get(sender);
            if (recievers.contains(user)) {
                count++;
            }
        }
        return count;
    }
    /* private static void DFS(String user,int v, boolean[] visited)
    {
        
        visited[v] = true;
        System.out.print(map.get(user).get(v) + " ");
        for (String x : map.get(user)) {
            if (!visited[x])
                DFS(x, visited);
        }
    }
     */
}
