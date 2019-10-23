//binary tree time complexity O(n), space complexity O(h)
//searching in a balanced BST time complexity O(log n)
//In case of binary search trees (BST), Inorder traversal gives nodes in non-decreasing order


// Lintcode 66 Bin Binary Tree Preorder Traversal
public class Solution {
    /**
     * @param root: A Tree
     * @return: Preorder in ArrayList which contains node values.
     */
     //recursion solution (divde and conquer)
    public List<Integer> preorderTraversal(TreeNode root) {
        // write your code here

        ArrayList<Integer> result = new ArrayList<Integer>();

        if (root == null){
            return result;
        }

        //Divide
        List<Integer> left = preorderTraversal(root.left);
        List<Integer> right = preorderTraversal(root.right);

        //Conquer
        result.add(root.val);
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    //iterative solution
    //使用stack LIFO 的性質， ans.add(root) -> push root.right -> push root.left
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> ans = new ArrayList<Integer>();
        if(root == null){
            return ans;
        }
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            ans.add(node.val);
            if(node.right != null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }
        }
        return ans;
    }
}


//l67 Binary Tree Inorder Traversal
//recursion (divide and conquer)
public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();

        if(root == null){
            return result;
        }

        //D
        List<Integer> left = inorderTraversal(root.left);
        List<Integer> right = inorderTraversal(root.right);

        result.addAll(left);
        result.add(root.val);
        result.addAll(right);
        return result;
}

//iterative
public List<Integer> inorderTraversal(TreeNode root) {
       // write your code here
       Stack<TreeNode> stack = new Stack();
       List<Integer> ans = new ArrayList<Integer>();
       while(root != null){
           stack.push(root);
           root = root.left;
       }
       while(!stack.isEmpty()){
           TreeNode cur = stack.pop();
           ans.add(cur.val);
           if(cur.right != null){
               TreeNode node = cur.right;
               while(node != null){
                   stack.push(node);
                   node = node.left;
               }
           }
       }
       return ans;
}

//lintcode 68 Binary Tree Postorder Traversal
//Recursive
public ArrayList<Integer> postorderTraversal(TreeNode root) {
    ArrayList<Integer> result = new ArrayList<Integer>();

    if (root == null) {
        return result;
    }

    result.addAll(postorderTraversal(root.left));
    result.addAll(postorderTraversal(root.right));
    result.add(root.val);

    return result;
}

//lintcode 69 Binary Tree Level Order Traversal
//use BFS for level order traversal (queue)
public List<List<Integer>> levelOrder(TreeNode root) {
        // write your code here
        List<List<Integer>> results = new ArrayList<>();
        if (root == null){
            return results;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);

        while(!queue.isEmpty()){
            ArrayList<Integer> level = new ArrayList<>();
            int size = queue.size();
            for(int i=0; i<size; i++){
                TreeNode node = queue.poll();
                level.add(node.val);
                if(node.left!=null){
                    queue.offer(node.left);
                }
                if(node.right!=null){
                    queue.offer(node.right);
                }
            }
            results.add(level);
        }
        return results;
}

//lintcode 71  Binary Tree Zigzag Level Order Traversal
//similar to normal level order traversal, the onlyt difference is that the order of traversinf
//each level is reversed from the last level. Used add(0, cur.val) to traverse from right to left.
//Used ans.size() % 2 == 1 to determine which order to inset the val
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if(root == null) return ans;
        queue.offer(root);
        while(!queue.isEmpty()){
            List<Integer> level_list = new ArrayList<>();
            int size = queue.size();
            for(int i = 0; i <size; ++i){
                TreeNode cur = queue.poll();
                if(ans.size() % 2 == 1){
                    level_list.add(0, cur.val);
                }
                else{
                    level_list.add(cur.val);
                }
                if(cur.left != null){
                    queue.offer(cur.left);
                }
                if(cur.right != null){
                    queue.offer(cur.right);
                }
            }
            ans.add(level_list);
        }
        return ans;
}

//Lintcode 97 Maximum Depth of Binary Tree
//divide and conquer
public int maxDepth(TreeNode root) {
       if(root == null){
           return 0;
       }
       int left = maxDepth(root.left);
       int right = maxDepth(root.right);
       return Math.max(left, right) + 1;
}

//lintcode 155 Minimum Depth of Binary Tree
public int minDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        if(left == 0){
            return right + 1;
        }
        if(right == 0){
            return left + 1;
        }
        return Math.min(left, right) + 1;
}

//version 2
public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getMin(root);
    }

public int getMin(TreeNode root){
    if (root == null) {
        return Integer.MAX_VALUE;
    }

    if (root.left == null && root.right == null) {
        return 1;
    }

    return Math.min(getMin(root.left), getMin(root.right)) + 1;
}


//lintcode 596 minimum subtree
//Given a binary tree, find the subtree with minimum sum. Return the root of the subtree.
//traverse + divide and conquer
   private TreeNode subtree = null;
   private int subtreeSum = Integer.MAX_VALUE;
   /**
    * @param root the root of binary tree
    * @return the root of the minimum subtree
    */
   public TreeNode findSubtree(TreeNode root) {
       helper(root);
       return subtree;
   }

   private int helper(TreeNode root) {
       if (root == null) {
           return 0;
       }

       int sum = helper(root.left) + helper(root.right) + root.val;
       if (sum <= subtreeSum) {
           subtreeSum = sum;
           subtree = root;
       }
       return sum;
   }

   //version 2 pure divide and conquer, resulttype template!
   // version 2: Pure divide conquer
class ResultType {
    public TreeNode minSubtree;
    public int sum, minSum;
    public ResultType(TreeNode minSubtree, int minSum, int sum) {
        this.minSubtree = minSubtree;
        this.minSum = minSum;
        this.sum = sum;
    }
}

public class Solution {
    /**
     * @param root the root of binary tree
     * @return the root of the minimum subtree
     */
    public TreeNode findSubtree(TreeNode root) {
        ResultType result = helper(root);
        return result.minSubtree;
    }

    public ResultType helper(TreeNode node) {
        if (node == null) {
            return new ResultType(null, Integer.MAX_VALUE, 0);
        }

        ResultType leftResult = helper(node.left);
        ResultType rightResult = helper(node.right);

        ResultType result = new ResultType(
            node,
            leftResult.sum + rightResult.sum + node.val,
            leftResult.sum + rightResult.sum + node.val
        );

        if (leftResult.minSum <= result.minSum) {
            result.minSum = leftResult.minSum;
            result.minSubtree = leftResult.minSubtree;
        }

        if (rightResult.minSum <= result.minSum) {
            result.minSum = rightResult.minSum;
            result.minSubtree = rightResult.minSubtree;
        }

        return result;
    }
}

//lintcode 93 Balanced Binary Tree
//Given a binary tree, determine if it is height-balanced
//once a subtree is not balanced, the whole tree is not balanced -> return -1
public boolean isBalanced(TreeNode root) {
        // write your code here
        if(root == null){
            return true;
        }
        return helper(root) != -1;
    }

    public int helper(TreeNode root){
        if(root == null){
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        if(left == -1 || right == -1 || Math.abs(left - right) > 1){
            return -1;
        }
        return Math.max(left, right) + 1;
  }

  //lintcode 597 Subtree with Maximum Average
  class Resulttype{
    public int sum, num;
    public double avg;
    public TreeNode avg_root;
    public Resulttype(int sum, int num, double avg, TreeNode root){
        this.sum = sum;
        this.num = num;
        this.avg = avg;
        this.avg_root = root;
    }
}
public class Solution {
    /**
     * @param root: the root of binary tree
     * @return: the root of the maximum average of subtree
     */
    public TreeNode findSubtree2(TreeNode root) {
        // write your code here
        if(root == null){
            return null;
        }
        return dfs(root).avg_root;
    }

    public Resulttype dfs(TreeNode root){
        if(root == null){
            return new Resulttype(0, 0, Integer.MIN_VALUE, null);
        }
        Resulttype left = dfs(root.left);
        Resulttype right = dfs(root.right);
        Resulttype result = new Resulttype(root.val + left.sum + right.sum, left.num + right.num + 1, (double)(root.val + left.sum + right.sum)/(left.num + right.num + 1), root);

        if(result.avg < left.avg){
            result.avg = left.avg;
            result.avg_root = left.avg_root;
        }
        if(result.avg < right.avg){
            result.avg = right.avg;
            result.avg_root = right.avg_root;
        }
        return result;
    }
}

//lintcode 453 Flatten Binary Tree to Linked List
//Flatten a binary tree to a fake "linked list" in pre-order traversal.
//Here we use the right pointer in TreeNode as the next pointer in ListNode.
//version 1 divide and Conquer
   public void flatten(TreeNode root) {
       helper(root);
   }

   // flatten root and return the last node
   private TreeNode helper(TreeNode root) {
       if (root == null) {
           return null;
       }

       TreeNode leftLast = helper(root.left);
       TreeNode rightLast = helper(root.right);

       // connect leftLast to root.right
       if (leftLast != null) {
           leftLast.right = root.right;
           root.right = root.left;
           root.left = null;
       }

       if (rightLast != null) {
           return rightLast;
       }

       if (leftLast != null) {
           return leftLast;
       }

       return root;
   }
//version 2 traverse
public TreeNode lastnode = null;
   public void flatten(TreeNode root) {
       // write your code here
       if(root == null){
           return;
       }
       if(lastnode != null){
           lastnode.left = null;
           lastnode.right = root;
       }

       lastnode = root;
       TreeNode right = root.right;
       flatten(root.left);
       flatten(right); //注意！！！
   }

//how about inorder?
//注意不需要  right = root.right!因为 inorder 左根右的顺序保证进入右子树的时候root.right不会被覆盖
//相反，preorder的话根左右的顺序中，进入右子树后root.right早已被覆盖！！！！！
if (root == null) {
    return;
}
flatten(root.left);
if(lastNode != null) {
  lastNode.left = null;
  lastNode.right = root;
}
lastNode = root;
flatten(root.right);

//version 3 右左跟反过来 *************
private TreeNode lastNode = null;
    /**
     * @param root: a TreeNode, the root of the binary tree
     * @return: nothing
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.right);
        flatten(root.left);

        root.right = lastNode;
        root.left = null;
        lastNode = root;
    }

//lintcode 88 Lowest Common Ancestor of a Binary Tree      LCA!!!
//如果left 跟 right 都有，返回当前root(lca)
//如果只有left非null, 返回left
//如果只有right非null, 返回right
//如果left, right都为null,返回null
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
       // 因为 “Assume two nodes are exist in tree”， 遇到 A 或 B 即可返回root
       if(root == null || root.val == A.val || root.val == B.val){
           return root;
       }

       TreeNode left = lowestCommonAncestor(root.left, A, B);
       TreeNode right = lowestCommonAncestor(root.right, A, B);
       //if one node exists in both left and right
       if(left != null && right != null){
           return root;
       }
       //if only exits in left
       if(left != null){
           return left;
       }
       //if only exits in right
       if(right != null){
           return right;
       }
       //if does not exist in either left or right
       return null;
}

//lintcode 474 Lowest Common Ancestor II
//The node has an extra attribute parent which point to the father of itself. The root's parent is null
public ParentTreeNode lowestCommonAncestorII(ParentTreeNode root, ParentTreeNode A, ParentTreeNode B) {
       // write your code here
       if(root == null || A == null || B == null){
           return null;
       }
       ParentTreeNode ret = null;
       List<ParentTreeNode> A_list = helper(A);
       List<ParentTreeNode> B_list = helper(B);
       int A_index = A_list.size() - 1;
       int B_index = B_list.size() - 1;

       while(A_index >= 0 && B_index >= 0){
           if(A_list.get(A_index) != B_list.get(B_index)){
               break;
           }
           ret = A_list.get(A_index);
           A_index--;
           B_index--;
       }
       return ret;

   }

   //used to return the list of path from current node to the root
   public List<ParentTreeNode> helper(ParentTreeNode root){
       List<ParentTreeNode> ret_list = new ArrayList<ParentTreeNode>();
       while(root != null){
           ret_list.add(root);
           root = root.parent;
       }
       return ret_list;
   }

//lintcode 578 Lowest Common Ancestor III
//node A or node B may not exist in tree
//used resulttype to store if A and B exist
class ResultType {
    public boolean a_exist, b_exist;
    public TreeNode node;
    ResultType(boolean a, boolean b, TreeNode n) {
        a_exist = a;
        b_exist = b;
        node = n;
    }
}

public class Solution {
    /**
     * @param root The root of the binary tree.
     * @param A and B two nodes
     * @return: Return the LCA of the two nodes.
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode A, TreeNode B) {
        // write your code here
        ResultType rt = helper(root, A, B);
        if (rt.a_exist && rt.b_exist)
            return rt.node;
        else
            return null;
    }

    public ResultType helper(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null)
            return new ResultType(false, false, null);

        ResultType left_rt = helper(root.left, A, B);
        ResultType right_rt = helper(root.right, A, B);
        // used || instead of | to support short-circuiting
        boolean a_exist = left_rt.a_exist || right_rt.a_exist || root == A;
        boolean b_exist = left_rt.b_exist || right_rt.b_exist || root == B;

        if (root == A || root == B)
            return new ResultType(a_exist, b_exist, root);

        if (left_rt.node != null && right_rt.node != null)
            return new ResultType(a_exist, b_exist, root);
        if (left_rt.node != null)
            return new ResultType(a_exist, b_exist, left_rt.node);
        if (right_rt.node != null)
            return new ResultType(a_exist, b_exist, right_rt.node);

        return new ResultType(a_exist, b_exist, null);
    }
}

//a more elegant method, without ResultType
public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode A, TreeNode B) {
    TreeNode res = divConq(root, A, B);
    if (foundA && foundB)    	// 程序执行完之后查看是否两个都找到
        return res;
    else
        return null;
}

private boolean foundA = false, foundB = false;
private TreeNode divConq(TreeNode root, TreeNode A, TreeNode B) {
    if (root == null)
        return root;

    TreeNode left = divConq(root.left, A, B);
    TreeNode right = divConq(root.right, A, B);

    // 如果 root 是要找的，更新全局变量
    if (root == A || root == B) {
        foundA = (root == A) || foundA;
        foundB = (root == B) || foundB;
        return root;
    }

    // 和 LCA 原题的思路一样
    if (left != null && right != null)
        return root;
    else if (left != null)	// 注意这种情况返回的时候是不保证两个都有找到的。可以是只找到一个或者两个都找到
        return left;		// 所以在最后上面要查看是不是两个都找到了
    else if (right != null)
        return right;
    return null;
}

//lintcode 1311 Lowest Common Ancestor of a Binary Search Tree
//由于BST的性质，如果一个大一个小的话LCA一定是root，不然在其一侧子树上，递归寻找即可
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
       // write your code here  BST的性质
       if(root == null){
           return null;
       }
       int cur_value = root.val;
       int p_value = p.val;
       int q_value = q.val;
       if(p_value > cur_value && q_value > cur_value){
           return lowestCommonAncestor(root.right, p, q);
       }
       if(p_value < cur_value && q_value < cur_value){
           return lowestCommonAncestor(root.left, p, q);
       }
       return root;
   }

   // lintcode 595 Binary Tree Longest Consecutive Sequence
   private int longest = 1;
      public int longestConsecutive(TreeNode root) {
          // write your code here
          helper(root);
          return longest;

      }

      public int helper(TreeNode root){
           if(root == null){
              return 0;
          }
          int left = helper(root.left);
          int right = helper(root.right);
          int cur_longest = 1; //at least we have a root with length 1
          if(root.left != null && root.val + 1 == root.left.val){
              cur_longest = Math.max(cur_longest, left + 1);
          }
          if(root.right != null && root.val + 1 == root.right.val){
              cur_longest = Math.max(cur_longest, right + 1);
          }
          if(cur_longest > longest){
              longest = cur_longest;
          }
          return cur_longest;
      }

   //lintcode 614 Binary Tree Longest Consecutive Sequence II
   //The path could be start and end at any node in the tree
   //记住二叉树中只可能上下一次！！！ /\
   //                             /  \
//          1
//         / \
//       2   0
//     /
//    3
//
// 0-1-2-3
   class Resulttype{
       public int max_up; //表示从此root开始往下最长递增sequence
       public int max_down;//表示从此root开始往下最长递减sequence
       public int max_len; //表示此root的子树中最长的consecutive sequence
       public Resulttype(int max, int min, int len){
           this.max_up = max;
           this.max_down = min;
           this.max_len = len;
       }
   }
   public class Solution {
       /**
        * @param root: the root of binary tree
        * @return: the length of the longest consecutive sequence path
        */
       public int longestConsecutive2(TreeNode root) {
           // write your code here
           if(root == null) return 0;
           return helper(root).max_len;
       }

       public Resulttype helper(TreeNode root){
           if(root == null){
               return new Resulttype(0, 0, 0);
           }

           Resulttype left = helper(root.left);
           Resulttype right = helper(root.right);
           int cur_longest = 1; //at least we have root 1
           int up = 0, down = 0;

           if(root.left != null && root.val + 1 == root.left.val){
               up = Math.max(up, left.max_up + 1);
           }
           if(root.left != null && root.val - 1 == root.left.val){
               down = Math.max(down, left.max_down + 1);
           }
            if(root.right != null && root.val + 1 == root.right.val){
               up = Math.max(up, right.max_up + 1);
           }
           if(root.right != null && root.val - 1 == root.right.val){
               down = Math.max(down, right.max_down + 1);
           }
           cur_longest += (up + down);
           cur_longest = Math.max(cur_longest, Math.max(left.max_len, right.max_len));
           return new Resulttype(up, down, cur_longest);
       }
   }

   //lintcode 619 Binary Tree Longest Consecutive Sequence III
   //very similar to lintcode 614, the only difference is to iterative through every child of each node

//             5
//          /     \
//         6       4
//        /|\     /|\
//       7 5 8   3 5 31
//
// return 5, // 3-4-5-6-7

   /**
    * Definition for a multi tree node.
    * public class MultiTreeNode {
    *     int val;
    *     List<TreeNode> children;
    *     MultiTreeNode(int x) { val = x; }
    * }
    */
   class Resulttype{
       public int max_up;
       public int max_down;
       public int max_length;
       public Resulttype(int up, int down, int len){
           this.max_up = up;
           this.max_down = down;
           this.max_length = len;
       }
   }
   public class Solution {
       /**
        * @param root the root of k-ary tree
        * @return the length of the longest consecutive sequence path
        */
       public int longestConsecutive3(MultiTreeNode root) {
           // Write your code here
           if(root == null){
               return 0;
           }
           return helper(root).max_length;
       }

       public Resulttype helper(MultiTreeNode root){
           if(root == null){
               return new Resulttype(0, 0, 0);
           }
           int up = 0, down = 0;
           int max_length = 0;
           for(MultiTreeNode node : root.children){
               Resulttype result = helper(node);
               if(root.val + 1 == node.val){
                   up = Math.max(up, result.max_up + 1);
               }
               if(root.val - 1 == node.val){
                   down = Math.max(down, result.max_down + 1);
               }
               if(result.max_length > max_length){
                   max_length = result.max_length;
               }
           }
           //up + down + 1 means the longest consecutive sequence when 转折点 is at root
           max_length = Math.max(up + down + 1, max_length);
           return new Resulttype(up, down, max_length);
       }
   }


//leetcode 124. Binary Tree Maximum Path Sum
//good problem, without use of result type
class Solution {
  int max_sum = Integer.MIN_VALUE;

  public int max_gain(TreeNode node) {
    if (node == null) return 0;

    // max sum on the left and right sub-trees of node
    int left_gain = Math.max(max_gain(node.left), 0); //use 0 here to deal with negative number
    int right_gain = Math.max(max_gain(node.right), 0);

    // the price to start a new path where `node` is a highest node
    int price_newpath = node.val + left_gain + right_gain;

    // update max_sum if it's better to start a new path
    max_sum = Math.max(max_sum, price_newpath);

    // for recursion :
    // return the max gain if continue the same path
    return node.val + Math.max(left_gain, right_gain);
  }

  public int maxPathSum(TreeNode root) {
    max_gain(root);
    return max_sum;
  }
}

//lintcode 376 Binary Tree Path Sum
//Given a binary tree, find all paths that sum of the nodes in the path equals to a given number target
public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
       // Algorithm: Traverse
       // Use recursion to traverse the tree in preorder, pass with a parameter
       // `sum` as the sum of each node from root to current node.
       // Put the whole path into result if the leaf has a sum equal to target.

       List<List<Integer>> result = new ArrayList<>();
       if (root == null) {
           return result;
       }

       ArrayList<Integer> path = new ArrayList<Integer>();
       path.add(root.val);
       helper(root, path, root.val, target, result);
       return result;
   }

   private void helper(TreeNode root,
                       ArrayList<Integer> path,
                       int sum,
                       int target,
                       List<List<Integer>> result) {

       // meet leaf
       if (root.left == null && root.right == null) {
           if (sum == target) {
               result.add(new ArrayList<Integer>(path)); //deep copy !!!!
           }
           return;
       }

       // go left
       if (root.left != null) {
           path.add(root.left.val);
           helper(root.left, path, sum + root.left.val, target, result);
           path.remove(path.size() - 1); //backtracking !!!!!!
       }

       // go right
       if (root.right != null) {
           path.add(root.right.val);
           helper(root.right, path, sum + root.right.val, target, result);
           path.remove(path.size() - 1);
       }
   }

//version 2 divide and Conquer
public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
       // write your code here
       List<List<Integer>> ans = new ArrayList<>();

       if(root == null){
           return ans;
       }

       if(root.left == null && root.right == null && root.val == target){
           List<Integer> path = new ArrayList<Integer>();
           path.add(root.val);
           ans.add(path);
           return ans;
       }

       List<List<Integer>> left = binaryTreePathSum(root.left, target - root.val);
       List<List<Integer>> right = binaryTreePathSum(root.right, target - root.val);

       for(List<Integer> mypath : left){
           mypath.add(0, root.val);
           ans.add(mypath);
       }

       for(List<Integer> mypath : right){
           mypath.add(0, root.val);
           ans.add(mypath);
       }
       return ans;
   }

// lintcode 246 Binary Tree Path Sum II
//The path does not need to start or end at the root or a leaf, but it must go in a straight line down
//注意与 376 backtrack remove 两次区别！
  public List<List<Integer>> binaryTreePathSum2(TreeNode root, int target) {
       // Write your code here
       List<List<Integer>> results = new ArrayList<List<Integer>>();
       ArrayList<Integer> buffer = new ArrayList<Integer>();
       if (root == null)
           return results;
       findSum(root, target, buffer, 0, results);
       return results;
   }

   public void findSum(TreeNode head, int sum, ArrayList<Integer> buffer, int level, List<List<Integer>> results) {
       if (head == null) return;
       int tmp = sum;
       buffer.add(head.val);
       for (int i = level;i >= 0; i--) {
           tmp -= buffer.get(i);
           if (tmp == 0) {
               List<Integer> temp = new ArrayList<Integer>();
               for (int j = i; j <= level; ++j)
                   temp.add(buffer.get(j));
               results.add(temp);
           }
       }
       findSum(head.left, sum, buffer, level + 1, results);
       findSum(head.right, sum, buffer, level + 1, results);
       buffer.remove(buffer.size() - 1);
   }

// lintcode 472 Binary Tree Path Sum III, hard!
//the path could be start and end at any node in the tree, with parent pointer
/*
 * Definition of ParentTreeNode:
 *
 * class ParentTreeNode {
 *     public int val;
 *     public ParentTreeNode parent, left, right;
 * }
 */
 //思路
// 现在的题意是可以从任何一点出发，而且有parent节点。
// 那么我们其实是traverse一遍这棵树，在每一点，我们出发找有没有符合条件的路径。
// 在每一点我们可以有三个方向：左边，右边，和上面。但是我们需要避免回头，所以需要一个from节点的参数。
public List<List<Integer>> binaryTreePathSum3(ParentTreeNode root, int target) {
       // write your code here
       List<List<Integer>> ans = new ArrayList<>();
       if(root == null){
           return ans;
       }
       dfs(root, target, ans);
       return ans;
   }
   //枚举每一个node作为起始点
   public void dfs(ParentTreeNode root, int target, List<List<Integer>> ans){
       if(root == null){
           return;
       }
       List<Integer> path = new ArrayList<>();
       findsum(root, target, ans, path, null);

       dfs(root.left, target, ans);
       dfs(root.right, target, ans);
   }
   //以此 node 为 root 遍历整颗树搜索 sum
   public void findsum(ParentTreeNode root, int target, List<List<Integer>> ans, List<Integer> path, ParentTreeNode from){
       if(root == null){
           return ;
       }
       path.add(root.val);
       target -= root.val;
       if(target == 0){
           ans.add(new ArrayList<Integer>(path));
       }
       if(root.left != null && root.left != from){   //向左儿子走
           findsum(root.left, target, ans, path, root);
       }
        if(root.parent != null && root.parent != from){ //向父节点走
           findsum(root.parent, target, ans, path, root);
       }
        if(root.right != null && root.right != from){ //向右儿子走
           findsum(root.right, target, ans, path, root);
       }
       path.remove(path.size() - 1);  //backtracking
   }

//lintcode 95 Validate Binary Search Tree
public boolean isValidBST(TreeNode root) {
    if(root == null) return true;
    return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
 public boolean helper(TreeNode root, long min, long max){
    if(root == null){
        return true;
    }


    boolean left = helper(root.left, min, root.val);
    boolean right = helper(root.right, root.val, max);

    if(root.val <= min || root.val >= max){
        return false;
    }
    return left && right;
}

//lintcode 378 Convert Binary Tree to Doubly Linked List
//simply do an inorder traversal and make use of dummy node
public class Solution {
    /**
     * @param root: The root of tree
     * @return: the head of doubly list node
     */
    private DoublyListNode dummy = null;
    private DoublyListNode prev = null;
    public DoublyListNode bstToDoublyList(TreeNode root) {
        // write your code here
        if(root == null){
            return null;
        }
        prev = new DoublyListNode(-1);
        dummy = prev;
        helper(root);
        return dummy.next;
    }

    private void helper(TreeNode root){
        if(root == null){
            return;
        }
        helper(root.left);
        connect(root);
        helper(root.right);
    }

    private void connect(TreeNode root){
        if(root == null){
            return;
        }
        DoublyListNode prev_node = prev;
        DoublyListNode node = new DoublyListNode(root.val);
        node.prev = prev_node;
        prev_node.next = node;
        prev = node;
    }
}
