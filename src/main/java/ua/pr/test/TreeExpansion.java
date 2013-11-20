package ua.pr.test;
import java.awt.event.*;  
import java.util.Enumeration;  
import javax.swing.*;  
import javax.swing.tree.*;  
   
public class TreeExpansion implements ActionListener  
{  
    JTree tree;  
   
    public void actionPerformed(ActionEvent e)  
    {  
        JButton button = (JButton)e.getSource();  
        String ac = button.getActionCommand();  
        if(ac.equals("expand"))  
        {  
            DefaultMutableTreeNode node = getLastExpandableNode();  
            setExpandedState((String)node.getUserObject(), true);  
        }  
        if(ac.equals("collapse"))  
        {  
            DefaultMutableTreeNode node = getFirstCollapsableNode();  
            setExpandedState((String)node.getUserObject(), false);  
        }  
    }  
   
    private DefaultMutableTreeNode getFirstCollapsableNode()  
    {  
        DefaultMutableTreeNode root =  
                (DefaultMutableTreeNode)tree.getModel().getRoot();  
        Enumeration<?> e = root.depthFirstEnumeration();  
        while(e.hasMoreElements())  
        {  
            DefaultMutableTreeNode n = (DefaultMutableTreeNode)e.nextElement();  
            if(n.getChildCount() > 0 && tree.isExpanded(new TreePath(n.getPath())))  
                return n;  
        }  
        return root;  
    }  
   
    private DefaultMutableTreeNode getLastExpandableNode()  
    {  
        DefaultMutableTreeNode root =  
                (DefaultMutableTreeNode)tree.getModel().getRoot();  
        DefaultMutableTreeNode lastNode = root;  
        Enumeration<?> e = root.depthFirstEnumeration();  
        while(e.hasMoreElements())  
        {  
            DefaultMutableTreeNode n = (DefaultMutableTreeNode)e.nextElement();  
            if(n.getChildCount() > 0 && n.getDepth() == 1 &&  
                      !tree.isExpanded(new TreePath(n.getPath())))  
                lastNode = n;  
        }  
        return lastNode;  
    }  
   
    private void setExpandedState(String id, boolean expand)  
    {  
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();  
        Enumeration<?> e = root.breadthFirstEnumeration();  
        while(e.hasMoreElements())  
        {  
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();  
            if(node.getUserObject().equals(id))  
            {  
                TreePath path = new TreePath(node.getPath());  
                expandNode(path, expand);  
                break;  
            }  
        }  
    }  
   
    private void expandNode(TreePath parent, boolean expand)  
    {  
        TreeNode node = (TreeNode)parent.getLastPathComponent();  
        if (node.getChildCount() >= 0)  
        {  
            Enumeration<?> e = node.children();  
            while(e.hasMoreElements())  
            {  
                TreeNode n = (TreeNode)e.nextElement();  
                TreePath path = parent.pathByAddingChild(n);  
                expandNode(path, expand);  
            }  
        }  
        if(expand)  
            tree.expandPath(parent);  
        else  
            tree.collapsePath(parent);  
    }  
   
    private JTree getTree()  
    {  
        DefaultMutableTreeNode  
            root = new DefaultMutableTreeNode("root"),  
            leaf;  
        DefaultMutableTreeNode[]  
            nodes    = new DefaultMutableTreeNode[3],  
            subNodes = new DefaultMutableTreeNode[8];  
        int[][] keys = {  { 1, 2, 3 }, { 3 }, { 1, 2, 3, 4 }  };  
        int subIndex = 0;  
        for(int j = 0; j < nodes.length; j++)  
        {  
            nodes[j] = new DefaultMutableTreeNode("node " + (j+1));  
            root.insert(nodes[j], j);  
            for(int k = 0; k < keys[j].length; k++, subIndex++)  
            {  
                String id = "sub " + (j+1) + (k+1);  
                subNodes[subIndex] = new DefaultMutableTreeNode(id);  
                nodes[j].insert(subNodes[subIndex], k);  
                for(int m = 0; m < keys[j][k]; m++)  
                {  
                    id = "leaf " + (j+1) + (k+1) + (m+1);  
                    leaf = new DefaultMutableTreeNode(id);  
                    subNodes[subIndex].insert(leaf, m);  
                    if(j == 1 && k == 0 && m == 1)  
                    {  
                        DefaultMutableTreeNode n = new DefaultMutableTreeNode("2121");  
                        leaf.insert(n, 0);  
                        DefaultMutableTreeNode p = new DefaultMutableTreeNode("21211");  
                        n.insert(p, 0);  
                    }  
                }  
            }  
        }  
        DefaultTreeModel model = new DefaultTreeModel(root);  
        tree = new JTree(model);  
        return tree;  
    }  
   
    private Box getControls()  
    {  
        JButton collapse = new JButton("collapse");  
        JButton expand = new JButton("expand");  
        collapse.setActionCommand("collapse");  
        expand.setActionCommand("expand");  
        collapse.addActionListener(this);  
        expand.addActionListener(this);  
        Box box = Box.createHorizontalBox();  
        box.add(Box.createHorizontalGlue());  
        box.add(collapse);  
        box.add(Box.createHorizontalGlue());  
        box.add(expand);  
        box.add(Box.createHorizontalGlue());  
        return box;  
    }  
   
    public static void main(String[] args)  
    {  
        TreeExpansion test = new TreeExpansion();  
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(test.getTree()));  
        f.getContentPane().add(test.getControls(), "South");  
        f.setSize(400,400);  
        f.setLocation(575,200);  
        f.setVisible(true);  
    }  
}  
