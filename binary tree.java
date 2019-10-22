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
//use BST for level order traversal (queue)
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
       flatten(right);
   }
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
