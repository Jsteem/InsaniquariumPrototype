package insaniquarium.game.gamesystem;



public class KDTree {
    private Node root;

    public boolean isEmpty(){
        return root == null;
    }

    public void add(CollisionObject collisionObject) {
        if (root == null) {
            root = new Node(collisionObject);
        } else {
            add(root, collisionObject, 0);
        }
    }

    private void add(Node node, CollisionObject item, int depth) {
        if (depth % 2 == 0) {
            if (item.x < node.collisionObject.x) {
                if (node.left == null) {
                    node.left = new Node(item);
                } else {
                    add(node.left, item, depth + 1);
                }
            } else {
                if (node.right == null) {
                    node.right = new Node(item);
                } else {
                    add(node.right, item, depth + 1);
                }
            }
        } else {
            if (item.y < node.collisionObject.y) {
                if (node.left == null) {
                    node.left = new Node(item);
                } else {
                    add(node.left, item, depth + 1);
                }
            } else {
                if (node.right == null) {
                    node.right = new Node(item);
                } else {
                    add(node.right, item, depth + 1);
                }
            }
        }

    }

    //search nearest collisionobject given a
    public CollisionObject searchNearest(float x, float y) {
        if (root == null) {
            return null;
        }
        Node bestNode = searchNearest(root, x, y, root, Double.POSITIVE_INFINITY);
        return bestNode.collisionObject;
    }

    private Node searchNearest(Node node, float x, float y, Node bestNode, double bestDistance) {
        if (node == null) {
            return bestNode;
        }
        double distance = distance(node.collisionObject, x, y);
        if (distance < bestDistance) {
            bestDistance = distance;
            bestNode = node;
        }
        Node nextNode, otherNode;
        if (node.isLeaf() || (node.left == null && x >= node.collisionObject.x) || (node.right == null && x < node.collisionObject.x)) {
            nextNode = x < node.collisionObject.x ? node.left : node.right;
            otherNode = x < node.collisionObject.x ? node.right : node.left;
        } else {
            nextNode = y < node.collisionObject.y ? node.left : node.right;
            otherNode = y < node.collisionObject.y ? node.right : node.left;
        }
        bestNode = searchNearest(nextNode, x, y, bestNode, bestDistance);
        if ((node.isLeaf() && distance > bestDistance) || (otherNode != null && distance > Math.abs(x - node.collisionObject.x))) {
            bestNode = searchNearest(otherNode, x, y, bestNode, bestDistance);
        }
        return bestNode;
    }

    private double distance(CollisionObject o, float x2, float y2) {
        float x1 = o.x;
        float y1 = o.y;
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
    private static class Node {
        private final CollisionObject collisionObject;
        private Node left;
        private Node right;

        public Node(CollisionObject collisionObject) {
            this.collisionObject = collisionObject;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }


    }

}