package TreePackage;

import java.util.Iterator;
import java.util.NoSuchElementException;

import QueuePackage.LinkedQueue;
import QueuePackage.QueueInterface;
import StackPackage.*;

public class BinaryTree<T> implements BinaryTreeInterface<T>
{
    protected BinaryNode<T> root;

    public BinaryTree()
    {
        root = null;
    }

    public BinaryTree(T rootData)
    {
        root = new BinaryNode<>(rootData);
    }

    public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree)
    {
        initializeTree(rootData, leftTree, rightTree);
    }

    @Override
    public T getRootData()
    {
        if(!isEmpty())
        {
            return root.getData();
        }
        return null;
    }

    @Override
    public int getHeight()
    {
        return root.getHeight();
    }

    @Override
    public int getNumberOfNodes()
    {
        return root.getNumberOfNodes();
    }

    @Override
    public Iterator<T> getPreorderIterator()
    {
        return new PreOrderIterator();
    }

    @Override
    public Iterator<T> getPostorderIterator()
    {
        return null;
    }

    @Override
    public void clear()
    {
        root = null;
    }

    @Override
    public Iterator<T> getInorderIterator()
    {
        return new InOrderIterator();
    }

    @Override
    public Iterator<T> getLevelOrderIterator()
    {
        return new LevelOrderIterator();
    }

    @Override
    public void setTree(T rootData)
    {
        setTree(rootData, null, null);
    }

    @Override
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree)
    {
        initializeTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
    }

    private void initializeTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree)
    {
        root = new BinaryNode<>(rootData);

        if((leftTree != null) && !leftTree.isEmpty())
            root.setLeftChild(leftTree.root);
        if((rightTree != null) && !rightTree.isEmpty())
        {
            if (rightTree != leftTree)
            {
                root.setRightChild(rightTree.root);
            }
            else
                root.setRightChild(rightTree.root.copy());
        }
        if((leftTree != null) && (leftTree != this))
        {
            leftTree.clear();
        }
        if((rightTree != null) && (rightTree != this))
        {
            rightTree.clear();
        }
    }

    @Override
    public boolean isEmpty()
    {
        return root == null;
    }

    // traversal that doesn't use an iterator (for demonstration purposes only)
    public void iterativeInorderTraverse()
    {
        StackInterface<BinaryNode<T>> nodeStack = new ArrayStack<>();
        BinaryNode<T> currentNode = root;

        while (!nodeStack.isEmpty() || (currentNode != null))
        {
            while (currentNode != null)
            {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            if (!nodeStack.isEmpty())
            {
                BinaryNode<T> nextNode = nodeStack.pop();

                System.out.println(nextNode.getData());
                currentNode = nextNode.getRightChild();
            }
        }
    }

    private class InOrderIterator implements Iterator<T>
    {
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public InOrderIterator()
        {
            nodeStack = new ArrayStack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        @Override
        public T next()
        {
            BinaryNode<T> nextNode = null;

            // find leftmost node with no left child
            while(currentNode != null)
            {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            if(!nodeStack.isEmpty())
            {
                nextNode = nodeStack.pop();
                currentNode = nextNode.getRightChild();
            }
            else
                throw new NoSuchElementException();
            return nextNode.getData();
        }
    }
    private class PreOrderIterator implements Iterator<T>
    {
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public PreOrderIterator()
        {
            nodeStack = new ArrayStack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        @Override
        public T next()
        {
            BinaryNode<T> nextNode = null;

            if (currentNode == root) {
                nodeStack.push(currentNode);
            }
            if (hasNext()) {
                nextNode = nodeStack.pop();
            }
            if (currentNode.hasRightChild() || currentNode.hasLeftChild()) {
                if (currentNode.hasRightChild()) {
                    nodeStack.push(currentNode.getRightChild());
                    if (!currentNode.hasLeftChild()) {
                        currentNode = currentNode.getRightChild();
                    }
                }
                if (currentNode.hasLeftChild()) {
                    nodeStack.push(currentNode.getLeftChild());
                    currentNode = currentNode.getLeftChild();
                }

            }
            else if (!currentNode.hasLeftChild() && !currentNode.hasRightChild()) {
                if (!nodeStack.isEmpty()) {
                    currentNode = nodeStack.peek();
                }
                else{
                    currentNode = null;
                }
            }
            else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }
    }
    private class LevelOrderIterator implements Iterator<T>
    {
        private QueueInterface<BinaryNode<T>> nodeQueue;
        private BinaryNode<T> currentNode;

        public LevelOrderIterator()
        {
            nodeQueue = new LinkedQueue<>();
            currentNode = root;
            int level = 0;
        }

        @Override
        public boolean hasNext()
        {
            return !nodeQueue.isEmpty() || (currentNode != null);
        }

        @Override
        public T next()
        {
            BinaryNode<T> nextNode = null;

            if (currentNode == root) {
                nodeQueue.enqueue(currentNode);
            }

            if (!nodeQueue.isEmpty()) {
                if (nodeQueue.getFront().hasLeftChild() || nodeQueue.getFront().hasRightChild()) {
                    if (nodeQueue.getFront().hasLeftChild()) {
                        nodeQueue.enqueue(currentNode.getLeftChild());
                    }
                    if (nodeQueue.getFront().hasRightChild()) {
                        nodeQueue.enqueue(currentNode.getRightChild());
                    }
                }
                nextNode = nodeQueue.dequeue();
                if (!nodeQueue.isEmpty()) {
                    currentNode = nodeQueue.getFront();
                }
                else {
                    currentNode = null;
                }
            }

            else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }

    }

}
