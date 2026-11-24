import java.util.*;

/**
 * Mini Social Network (PS No. 94)
 * Concepts: Graph (adjacency list), BFS, Hashing (HashMap), Linked List
 *
 * - Users are stored in a HashMap.
 * - Follow relationships are a directed graph (adjacency list).
 * - Each user's posts are a singly linked list, newest post at the head.
 * - Feed = k-way merge of the followed users' lists (newest / highest ID first).
 * - Suggestions = BFS up to depth 2 on the follow graph.
 */
public class MiniSocialNetwork {

    // ---------- Post: one node of a user's linked list ----------
    static class Post {
        final int id;
        final String author;
        final String content;
        Post next;

        Post(int id, String author, String content) {
            this.id = id;
            this.author = author;
            this.content = content;
        }
    }

    // ---------- PostList: singly linked list, newest at head ----------
    static class PostList {
        Post head;

        void addFirst(Post p) {      // O(1)
            p.next = head;
            head = p;
        }
    }

    // ---------- User ----------
    static class User {
        final String name;           // display name
        final PostList posts = new PostList();

        User(String name) {
            this.name = name;
        }
    }

    // ---------- The network ----------
    static class SocialNetwork {
        private final HashMap<String, User> users = new HashMap<>();                 // key: lowercase name
        private final HashMap<String, LinkedList<String>> following = new HashMap<>(); // directed graph
        private int nextPostId = 1;

        private static String key(String name) {
            return name.trim().toLowerCase();
        }

        private static boolean validName(String name) {
            return name != null && name.matches("[A-Za-z0-9_]+");
        }

        // Register one or more users (comma separated)
        String register(String input) {
            if (input == null || input.trim().isEmpty()) {
                return "Invalid username.";
            }
            String[] names = input.split(",");
            StringBuilder out = new StringBuilder();
            int created = 0;
            for (String raw : names) {
                String name = raw.trim();
                if (!validName(name)) {
                    out.append("Invalid username: '").append(name).append("'.\n");
                } else if (users.containsKey(key(name))) {
                    out.append("User ").append(name).append(" already exists.\n");
                } else {
                    users.put(key(name), new User(name));
                    following.put(key(name), new LinkedList<>());
                    created++;
                }
            }
            if (created > 0) {
                out.append(created == 1 ? "User registered." : "Users registered.");
            }
            return out.toString().trim();
        }

        // a follows b
        String follow(String a, String b) {
            User ua = users.get(key(a));
            User ub = users.get(key(b));
            if (ua == null) return "User " + a.trim() + " not found.";
            if (ub == null) return "User " + b.trim() + " not found.";
            if (ua == ub) return "You cannot follow yourself.";
            if (following.get(key(a)).contains(key(b))) {
                return ua.name + " already follows " + ub.name + ".";
            }
            following.get(key(a)).add(key(b));
            return ua.name + " now follows " + ub.name + ".";
        }

        // a publishes a post
        String post(String a, String content) {
            User ua = users.get(key(a));
            if (ua == null) return "User " + a.trim() + " not found.";
            if (content == null || content.trim().isEmpty()) return "Post cannot be empty.";
            Post p = new Post(nextPostId++, ua.name, content.trim());
            ua.posts.addFirst(p);
            return "Post " + p.id + " created.";
        }

        // Feed: merge the linked lists of all followed users, highest ID first
        String feed(String a) {
            User ua = users.get(key(a));
            if (ua == null) return "User " + a.trim() + " not found.";

            PriorityQueue<Post> pq = new PriorityQueue<>((x, y) -> y.id - x.id);
            for (String f : following.get(key(a))) {
                Post head = users.get(f).posts.head;
                if (head != null) pq.add(head);
            }
            if (pq.isEmpty()) return "Your feed is empty.";

            StringBuilder sb = new StringBuilder();
            boolean first = true;
            while (!pq.isEmpty()) {
                Post p = pq.poll();
                if (first) {
                    sb.append("Feed: ");
                    first = false;
                }
                sb.append("[Post ").append(p.id).append("] ")
                  .append(p.author).append(": ").append(p.content).append("\n");
                if (p.next != null) pq.add(p.next);
            }
            return sb.toString().trim();
        }

        // Suggestions: BFS up to depth 2 on the follow graph
        String suggest(String a) {
            User ua = users.get(key(a));
            if (ua == null) return "User " + a.trim() + " not found.";

            String src = key(a);
            HashSet<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            HashMap<String, Integer> depth = new HashMap<>();
            LinkedHashMap<String, String> suggestions = new LinkedHashMap<>(); // candidate -> via

            visited.add(src);
            queue.add(src);
            depth.put(src, 0);

            while (!queue.isEmpty()) {
                String cur = queue.poll();
                int d = depth.get(cur);
                if (d == 2) continue;                 // do not go deeper than depth 2
                for (String nb : following.get(cur)) {
                    if (visited.contains(nb)) continue;
                    visited.add(nb);
                    depth.put(nb, d + 1);
                    queue.add(nb);
                    if (d + 1 == 2) {
                        suggestions.put(nb, users.get(cur).name);
                    }
                }
            }

            if (suggestions.isEmpty()) return "No suggestions available.";
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> e : suggestions.entrySet()) {
                sb.append("Suggested: ").append(users.get(e.getKey()).name)
                  .append(" (followed by ").append(e.getValue()).append(")\n");
            }
            return sb.toString().trim();
        }
    }

    // ---------- Built-in demo of the 4 test cases ----------
    static void runTestCases() {
        System.out.println("===== Test Case 1: Normal Input =====");
        SocialNetwork n = new SocialNetwork();
        System.out.println(n.register("Riya, Aman"));
        System.out.println(n.follow("Riya", "Aman"));
        System.out.println(n.post("Aman", "Hello DSA"));
        System.out.println(n.feed("Riya"));

        System.out.println("\n===== Test Case 2: Boundary / Minimum Input =====");
        n = new SocialNetwork();
        n.register("Karan");
        System.out.println(n.feed("Karan"));

        System.out.println("\n===== Test Case 3: Invalid Input =====");
        n = new SocialNetwork();
        n.register("Riya");
        System.out.println(n.follow("Riya", "Neha"));

        System.out.println("\n===== Test Case 4: Different Valid Scenario =====");
        n = new SocialNetwork();
        n.register("Riya, Aman, Karan");
        n.follow("Riya", "Aman");
        n.follow("Aman", "Karan");
        n.post("Aman", "Hello DSA");                 // Post 1
        System.out.println("(a) " + n.post("Karan", "Graphs rock"));
        System.out.println("(b) " + n.suggest("Riya"));
        System.out.println("(c) " + n.follow("Riya", "Karan"));
        System.out.println(n.feed("Riya"));
    }

    // ---------- Console menu ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SocialNetwork net = new SocialNetwork();

        while (true) {
            System.out.println("\n===== MINI SOCIAL NETWORK =====");
            System.out.println("1. Register user(s)");
            System.out.println("2. Follow a user");
            System.out.println("3. Publish a post");
            System.out.println("4. View feed");
            System.out.println("5. Follow suggestions");
            System.out.println("6. Run sample test cases");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            if (!sc.hasNextLine()) break;
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Username(s), comma separated: ");
                    System.out.println(net.register(sc.nextLine()));
                    break;
                case "2":
                    System.out.print("Your username: ");
                    String a = sc.nextLine();
                    System.out.print("Username to follow: ");
                    String b = sc.nextLine();
                    System.out.println(net.follow(a, b));
                    break;
                case "3":
                    System.out.print("Your username: ");
                    String author = sc.nextLine();
                    System.out.print("Post text: ");
                    String text = sc.nextLine();
                    System.out.println(net.post(author, text));
                    break;
                case "4":
                    System.out.print("Your username: ");
                    System.out.println(net.feed(sc.nextLine()));
                    break;
                case "5":
                    System.out.print("Your username: ");
                    System.out.println(net.suggest(sc.nextLine()));
                    break;
                case "6":
                    runTestCases();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
