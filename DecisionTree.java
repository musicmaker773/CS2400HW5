package TreePackage;
public class DecisionTree<T> extends BinaryTree<T> implements DecisionTreeInterface<T>
{
    BinaryNode<T> currentNode;
    public DecisionTree(T data)
    {
        this(data, null, null);
    }

    public DecisionTree(T data, DecisionTree<T> left, DecisionTree<T> right)
    {
        setTree(data, left, right);
    }

    @Override
    public T getCurrentData()
    {
        if (currentNode!= null)
        {
            return currentNode.getData();
        }
        return null;
    }

    @Override
    public void setCurrentData(T newData)
    {
        // TODO
        if (currentNode == null) {
            throw new IllegalArgumentException();
        }
        currentNode.setData(newData);
    }

    @Override
    public void setResponses(T responseForNo, T responseForYes)
    {
        // TODO
        if (currentNode == null) {
            throw new IllegalArgumentException();
        }
       BinaryNode<T> leftChild = new BinaryNode<>(responseForNo);
       BinaryNode<T> rightChild = new BinaryNode<>(responseForYes);
       currentNode.setLeftChild(leftChild);
       currentNode.setRightChild(rightChild);
    }

    @Override
    public boolean isAnswer()
    {
        // TODO
        return currentNode.isLeaf();
    }

    @Override
    public void advanceToNo()
    {
        // TODO
        currentNode = currentNode.getLeftChild();
    }

    @Override
    public void advanceToYes()
    {
        // TODO
        currentNode = currentNode.getRightChild();

    }

    @Override
    public void resetCurrentNode()
    {
        currentNode = root;
    }

    @Override
    public void setTree(T rootData)
    {
        super.setTree(rootData);
        currentNode = root;

    }

    @Override
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree)
    {
        super.setTree(rootData, leftTree, rightTree);
        currentNode = root;
    }
}
