# Mini Social Network

A console-based **Data Structures and Algorithms (DSA)** project developed using Java. This project demonstrates the implementation of basic social networking features using Graphs, BFS, HashMap, and Linked Lists.

## Project Information

- **Project Name:** Mini Social Network
- **Problem Statement No.:** 94
- **Programming Language:** Java
- **University:** Rungta International Skills University
- **Domain:** Data Structures and Algorithms (DSA)

## Features

- **User Registration:** Users can register with unique usernames.
- **Follow Users:** Users can follow other registered users.
- **Publish Posts:** Users can create and publish text posts.
- **Personalized News Feed:** View the latest posts from followed users.
- **Follow Suggestions:** Discover new users using BFS traversal up to depth 2.
- **Input Validation:** Prevent duplicate usernames, invalid users, self-following, and duplicate follow relationships.
- **Empty Feed Handling:** Display an appropriate message when no posts are available.

## Data Structures Used

| Data Structure | Purpose |
|---|---|
| HashMap | Stores registered users with usernames as keys. |
| Graph | Represents user follow relationships. |
| Adjacency List | Stores connections between users. |
| Linked List | Maintains posts published by each user. |
| Queue | Used for BFS traversal. |
| HashSet | Tracks visited users during BFS. |

## Algorithms Used

### 1. Breadth-First Search (BFS)

BFS is used to suggest users that a person may want to follow.

- Starts from the selected user.
- Explores connected users level by level.
- Searches up to depth 2.
- Excludes the current user and users already followed.

### 2. Graph Traversal

A directed graph represents follow relationships.

For example:

`Riya → Aman`

This means Riya follows Aman, but Aman does not automatically follow Riya.

## How the Project Works

1. Start the Java application.
2. Register users with unique usernames.
3. Users can follow other registered users.
4. Users can publish posts.
5. The application stores posts in Linked Lists.
6. Users can view their personalized news feed.
7. BFS explores the graph to suggest new users.
8. The application displays results and returns to the main menu.

## System Requirements

- Java JDK 17 or later
- IntelliJ IDEA / Eclipse / VS Code
- Windows / Linux / macOS
- Minimum 4 GB RAM recommended

## Installation and Execution

### Step 1: Clone the Repository

```bash
git clone <your-repository-url>
```

### Step 2: Open the Project Directory

```bash
cd Mini-Social-Network
```

### Step 3: Compile the Java Files

If your project contains multiple Java files in the same directory:

```bash
javac *.java
```

### Step 4: Run the Application

```bash
java Main
```

**Note:** Make sure `Main` is the name of the class containing the `main()` method. Adjust the commands according to your actual project structure.

## Sample Test Cases

| Test Case | Expected Output |
|---|---|
| Register Riya and Aman | Users registered. |
| Riya follows Aman | Riya now follows Aman. |
| Aman publishes Hello DSA | Post created. |
| Riya views feed | Aman’s post appears. |
| Karan views an empty feed | Your feed is empty. |
| Riya follows unregistered Neha | User Neha not found. |

## Time Complexity Analysis

Let:
- V = Number of users
- E = Number of follow relationships
- P = Number of posts collected
- d = Number of users followed by one user

| Operation | Time Complexity |
|---|---|
| User Registration | O(1) Average |
| User Lookup | O(1) Average |
| Follow Operation | O(d) for list-based duplicate checking |
| Publish Post | O(1) |
| Generate Feed | O(P log P) |
| BFS Traversal | O(V + E) Worst Case |

## Project Structure

Example structure (adjust according to your actual Java files):

```text
Mini-Social-Network/
│
├── Main.java
├── SocialNetwork.java
├── User.java
├── Post.java
│
└── README.md
```

## Future Scope

- Database integration for permanent storage.
- User profile management.
- Like and comment functionality.
- Unfollow functionality.
- Graphical User Interface (GUI).
- Improved user recommendations.
- Authentication and privacy features.

## Limitations

- Data is stored in memory.
- Data is lost when the application terminates.
- The application uses a console-based interface.
- Only text-based posts are supported.

## Author

**Archana Choure**

Bachelor of Technology – Computer Science and Engineering

Rungta International Skills University

**Project:** Mini Social Network  
**Subject:** Data Structures and Algorithms (DSA)

---

*Developed as an academic DSA project to demonstrate practical applications of Graphs, BFS, HashMap, and Linked Lists using Java.*
