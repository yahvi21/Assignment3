import java.util.*;
import java.io.*;

/**
 *
 * @author yahvibhatnagar
 */
class Assignment3 {

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
            System.out.printf("* %s has sent messages to %d others\n", email, x);
            System.out.printf("* %s has received messages from %d others\n", email, y);
            System.out.printf("* %s is in a team with %d individuals\n", email, z);
            if (args.length == 2) {

                try {
                    FileWriter writer = new FileWriter(args[1], true);
                    writer.write(email + "-" + z + "\n");
                    writer.close();
                } catch (IOException ex) {

                }

            }
        }
    }

    //Using recursive method to read file 
    private static void readDataSet(String path) {

        File file = new File(path);

        if (file.isFile()) {
            readFile(path);
            ++fileCount;
        } else {
            // get a list of all the directories in the main directory
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                readDataSet(path + "/" + subFile.getName());
            }
        }

    }

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

                //read email after To on text file
                int start = text.indexOf("From: ") + 6;
                int end = text.indexOf("To: ");
                from = text.substring(start, end);

                //if (!from.endsWith("@enron.com")) {
                //  return;
                //}
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
                to = to.replaceAll("\\s+", "");
                String[] to_arr = to.split(",");
                for (String email : to_arr) {
                    if (!email.endsWith("@enron.com")) {
                        continue;
                    }
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

    // This method returns the number of emails sent by a specific user
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
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(map.get(user).get(v) + " ");
        // Recur for all the vertices
        // adjacent to this vertex
        for (String x : map.get(user)) {
            if (!visited[x])
                DFS(x, visited);
        }
    }
     */
}
