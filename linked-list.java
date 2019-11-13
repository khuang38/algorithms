//lintcode 105 Copy List with Random Pointer
//version 1 hashmap, Time Complexity O(N), Space Complexity O(N)
public RandomListNode copyRandomList(RandomListNode head) {
       // write your code here
       if(head == null){
           return null;
       }
       Map<RandomListNode, RandomListNode> map = new HashMap<>();
       RandomListNode new_root = new RandomListNode(-1);
       RandomListNode prev = new_root;
       RandomListNode newNode = new_root;
       while(head != null){
          if(map.containsKey(head)){
              newNode = map.get(head);
          }
           else{
               newNode = new RandomListNode(head.label);
               map.put(head, newNode);
           }
           prev.next = newNode;

           //copy random
           if(head.random != null){
               if(map.containsKey(head.random)){
                   newNode.random = map.get(head.random);
               }
               else{
                   newNode.random = new RandomListNode(head.random.label);
                   map.put(head.random, newNode.random);
               }

           }
            prev = newNode;
            head = head.next;
       }
       return new_root.next;
   }

  //version 2, Space Complexity O(1) ->add node in the original list...
  public Node copyRandomList(Node head) {
       if(head == null){
           return null;
       }
       Node dummy = new Node(-1);
       dummy.next = head;
       copylist(head);
       copyrandom(head);
       return splitlist(head);
   }

   public void copylist(Node head){
      // Node new_node = head.next;
       while(head != null){
           Node temp = new Node(head.val);
           temp.next = head.next;
           head.next = temp;
           head = head.next.next;
       }
   }

   public void copyrandom(Node head){
       while(head != null){
           if(head.random == null){
               head.next.random = null;
           }
           else{
               head.next.random = head.random.next;
           }
           head = head.next.next;
       }
   }

   public Node splitlist(Node head){
       Node copy_node = head.next;
       Node ret = copy_node;
       while(head != null){
           head.next = copy_node.next;
           head = head.next;
           if(copy_node.next == null){
               break;
           }
           copy_node.next = head.next;
           copy_node = copy_node.next;
       }
       return ret;
   }

//Leetcode 25 Reverse N nodes in K-group
//note: the return value of reverse is the next node of the k-grouo
   public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode cur = dummy;
        while(cur != null){
            cur = reverse(cur, k);
        }
        return dummy.next;
    }

    public ListNode reverse(ListNode head, int k){
        ListNode nk = head;
        for(int i = 0; i < k; ++i){
            nk = nk.next;
            if(nk == null){
                return null;
            }
        }
        //head still points to the last node does no need to be reversed
        ListNode prev = head;
        ListNode cur = head.next;
        ListNode fin = cur;
        while(cur != nk){
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        ListNode next = cur.next;
        cur.next = prev;
        head.next = cur;
        fin.next = next;
        return fin;
    }

//leetcode 86. Partition List
//create left and right
//left connects with nodes with smaller value than x, right connexts with nodes with bigger value than x
public ListNode partition(ListNode head, int x) {
      if(head == null || head.next == null){
          return head;
      }
      ListNode leftdummy = new ListNode(-1);
      ListNode rightdummy = new ListNode(-1);
      ListNode left = leftdummy;
      ListNode right = rightdummy;

      while(head != null){
          if(head.val < x){
              left.next = head;
              left = head;
          }
          else{
              right.next = head;
              right = head;
          }
          head = head.next;
      }
      right.next = null;
      left.next = rightdummy.next;
      return leftdummy.next;
  }
//leetcode 21 Merge Two Sorted Lists
//time complexity O(n+m), Space Complexity O(1)
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null){
            return null;
        }
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;
        while(l1 != null && l2 != null){
            if(l1.val <= l2.val){
                head.next = l1;
                l1 = l1.next;
            }
            else{
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        if(l1 == null){
            head.next = l2;
        }
        else{
            head.next = l1;
        }
        return dummy.next;
    }
//version 2 recursion
//TC O(M+N), SC O(M+N)
//The first call to mergeTwoLists does not return until the ends of both l1 and l2 have been reached, so n + mn+m stack frames consume O(n + m)O(n+m) space
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
       if (l1 == null) {
           return l2;
       }
       else if (l2 == null) {
           return l1;
       }
       else if (l1.val < l2.val) {
           l1.next = mergeTwoLists(l1.next, l2);
           return l1;
       }
       else {
           l2.next = mergeTwoLists(l1, l2.next);
           return l2;
       }

   }


//leetcode 92. Reverse Linked List II
//Reverse a linked list from position m to n. Do it in one-pass
//TC O(N), SC O(1)
public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null){
            return head;
        }
        int count = -1;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        head = dummy;
        ListNode prev = dummy;
        ListNode start = null;
        while(count < n){
            count++;
            ListNode next = head.next;
            if(count + 1 == m){
                start = head;
            }
            else{
                if(count >= m){
                    head.next = prev;
                }
            }
            prev = head;
            head = next;
        }
            start.next.next = head;
            start.next = prev;
        return dummy.next;
    }

//lintcode 511. Swap Two Nodes in Linked List
//Given a linked list and two values v1 and v2. Swap the two nodes in the linked list with values v1 and v2. It's guaranteed
// there is no duplicate values in the linked list. If v1 or v2 does not exist in the given linked list, do nothing.
//2 cases to consider : 1
//                     v1 and v2 not close to each other
//                      2
//                     v1 and v2 close to each other (v1 -> v2 or v2 -> v1, seperate cases!!!)
//TC O(N), SC O(1)
public ListNode swapNodes(ListNode head, int v1, int v2) {
       // write your code here
       if(head == null || head.next == null){
           return head;
       }
       ListNode dummy = new ListNode(-1);
       dummy.next = head;
       ListNode n1 = null;
       ListNode n2 = null;
       ListNode node = dummy;
       while(node.next != null){
           if(v1 == node.next.val){
               n1 = node;
           }
           else if(v2 == node.next.val){
               n2 = node;
           }
           node = node.next;
       }
       if(n1 == null || n2 == null){
           return head;
       }
       if(n1.next != n2 && n2.next != n1){
           ListNode temp1 = n1.next;
           ListNode temp2 = temp1.next;
           n1.next = n2.next;
           temp1.next = n2.next.next;
           n2.next.next = temp2;
           n2.next = temp1;
       }

       if(n1.next == n2){
           ListNode temp = n2.next.next;
           n1.next = n2.next;
           n1.next.next = n2;
           n2.next = temp;
       }
       if(n2.next == n1){
           ListNode temp = n1.next.next;
           n2.next = n1.next;
           n2.next.next = n1;
           n1.next = temp;
       }
       return dummy.next;
   }

   // leetcode 143. Reorder List
   //find mid point
   //reverse list
   //connect two lists
   // good problem !!!!!!
   class Solution {

    public ListNode findmid(ListNode head){
        ListNode slow = head, fast = head;
       // ListNode fast = head;
        //find the mid point
        while(fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }


    public ListNode reverse(ListNode cur){
        ListNode prev = null;
        while(cur != null){
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
    public void reorderList(ListNode head) {
        //find the mid point, split the list into two parts: left and right
        //reverse the right part
        //connect the two lists one node by one node, start from left

        if(head == null || head.next == null){
            return;
        }
        ListNode mid = findmid(head);


        //reverse the right part
        ListNode cur = mid.next;
        mid.next = null;
        ListNode n2 = reverse(cur);
        ListNode n1 = head;

        //connect two parts
       while(n1 != null && n2 != null){
           ListNode next = n1.next;
           n1.next = n2;
           n2 = n2.next;
           n1.next.next = next;
           n1 = next;
       }
        return ;
    }
}

//leetcode 61. Rotate List
//Given a linked list, rotate the list to the right by k places, where k is non-negative
//note, need k = k % length, or will TLE
//brutal method
public int getlength(ListNode head){
       int length = 0;
       while(head != null){
           length++;
           head = head.next;
       }
       return length;
   }
   public ListNode rotateRight(ListNode head, int k) {
       if(head == null || head.next == null){
           return head;
       }
       int length = getlength(head);
       k = k % length;
       ListNode dummy = new ListNode(-1);
       dummy.next = head;
       for(int i = 0; i < k; ++i){
           ListNode cur = dummy.next;
           while(cur.next != null && cur.next.next != null){
               cur = cur.next;
           }
           ListNode temp = dummy.next;
           dummy.next = cur.next;
           cur.next.next = temp;
           cur.next = null;
       }
       return dummy.next;
   }
//a more elegant way
private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length ++;
            head = head.next;
        }
        return length;
    }

    public ListNode rotateRight(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        int length = getLength(head);
        n = n % length;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;

        ListNode tail = dummy;
        for (int i = 0; i < n; i++) {
            head = head.next;
        }

        while (head.next != null) {
            tail = tail.next;
            head = head.next;
        }

        head.next = dummy.next;
        dummy.next = tail.next;
        tail.next = null;
        return dummy.next;
    }

//leetcode 141. Linked List Cycle
// two pointer, slow and fast
//TC O(N), SC O(1)
public boolean hasCycle(ListNode head) {
        if(head == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                return true;
            }
        }
        return false;
    }

//HASHSET  TC O(N), SC O(1)
public boolean hasCycle(ListNode head) {
    Set<ListNode> nodesSeen = new HashSet<>();
    while (head != null) {
        if (nodesSeen.contains(head)) {
            return true;
        } else {
            nodesSeen.add(head);
        }
        head = head.next;
    }
    return false;
}

//leetcode 142. Linked List Cycle II
//   2(a + b) = a + b + n(b + c)
//   a + b    = n(b + c)
//   a        = (n - 1)(b + c) + c
// which implies a = c !!!!
//from midpoint, fast goes to next every time, while slow starts from head, the new meet node will be the node want
public ListNode detectCycle(ListNode head) {
       if(head == null){
           return null;
       }
       ListNode dummy = new ListNode(-1);
       dummy.next = head;
       ListNode slow = head, fast = head;
       ListNode meet = null;
       while(fast != null && fast.next != null){
           fast = fast.next.next;
           slow = slow.next;
           if(fast == slow){
               meet = fast;
               break;
           }
       }
       if(meet == null){
           return null;
       }
       slow = head;
       while(fast != slow){
           fast = fast.next;
           slow = slow.next;
       }
       return fast;
   }

//leetcode 160. Intersection of Two Linked Lists
//version 1 trick : a + c + b = b + c + a   !!!!!!
//TC O(M + N), SC O(1)
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
       if(headA == null || headB == null){
           return null;
       }
       ListNode a = headA;
       ListNode b = headB;
       while(a != b){
           if(a == null){
               a = headB;
           }
           else a = a.next;

           if(b == null){
               b = headA;
           }
           else b = b.next;
       }
       return a;
   }

//version 2: use the equation in list cycle ii -> connect the tail of a to the start of b...
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
       if(headA == null || headB == null){
           return null;
       }
       ListNode a = headA;
       while(a.next != null){
           a = a.next;
       }
       a.next = headB;

       ListNode slow = headA;
       ListNode fast = headA;
       ListNode meet = null;
       while(fast != null && fast.next != null){
           fast = fast.next.next;
           slow = slow.next;
           if(slow == fast){
               meet = slow;
               break;
           }
       }
       if(meet == null){
           return null;
       }
       slow = headA;
       while(slow != fast){
           slow = slow.next;
           fast = fast.next;
       }
       a.next = null;
       return fast;
  }

  //lintcode 98. Sort List

  public ListNode sortList(ListNode head) {
          // write your code here
          if(head == null || head.next == null){
              return head;
          }
          ListNode slow = head;
          ListNode fast = head.next;
          while(fast != null && fast.next != null){
              slow = slow.next;
              fast = fast.next.next;
          }
          ListNode right_head = slow.next;
          slow.next = null;
          ListNode left = sortList(head);
          ListNode right = sortList(right_head);
          System.out.println(left.val);
          return merge(left, right);
      }

      public ListNode merge(ListNode l1, ListNode l2){
          if(l1 == null && l2 == null){
              return null;
          }
          if(l1 == null){
              return l2;
          }
          if(l2 == null){
              return l1;
          }
          ListNode dummy = new ListNode(-1);
          ListNode head = dummy;
          while(l1 != null && l2 != null){
              if(l1.val <= l2.val){
                  head.next = l1;
                  l1 = l1.next;
              }
              else{
                  head.next = l2;
                  l2 = l2.next;
              }
              head = head.next;
          }
          if(l1 == null){
              head.next = l2;
          }
          else{
              head.next = l1;
          }
          return dummy.next;
      }

  // leetcode 109. Convert Sorted List to Binary Search Tree
  public TreeNode sortedListToBST(ListNode head) {
       if(head == null){
           return null;
       }
       if(head.next == null){
           return new TreeNode(head.val);
       }
       ListNode mid = findmid(head);
       System.out.println(mid.val);
       TreeNode root = new TreeNode(mid.val);
       root.left = sortedListToBST(head);
       root.right = sortedListToBST(mid.next);
       return root;

   }

   public ListNode findmid(ListNode head){
       if(head == null || head.next == null){
           return head;
       }
       ListNode slow = head;
       ListNode fast = head;
       ListNode prev = null;
       while(fast != null && fast.next != null){
           prev = slow;
           fast = fast.next.next;
           slow = slow.next;
       }
       if(prev != null){
           prev.next = null;
       }
       return slow;
   }
