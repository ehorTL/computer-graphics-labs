import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {
	private Node root;
	private List<Point> pnts;

	public KdTree(List<Point> pnts) throws IOException {
		this.pnts = pnts;
		root = buildTree(new Rectangle(), new Line(Straight.VERTICAL, Direction.TOP), pnts);
		graphviz();
		//List<Point> list = findRegion(root, new Region(new Point(5, 4), new Point(7, 6)));
		List<Point> list = findRegion(root, new Region(new Point(-10, -6), new Point(8, 8)));
		if(list.size() == 0) {
			System.out.println("Nothing found");
		}
		else {
			System.out.println(list);
		}
	}

	public Node buildTree(Rectangle rect, Line line, List<Point> points) {
		if(points == null || points.size() == 0) {
			return new Node(rect, null); //leaf
		}
		if(line.getStraight() == Straight.HORIZONTAL) {
			points.sort(Comparator.comparingDouble(Point::getY));
			int middleIndex = (points.size()-1)/2;
			Point middle = points.get(middleIndex);
			line.setPoint(middle);
			Node node = new Node(rect, line);
			if(line.getDirection() == Direction.RIGHT) {
				Rectangle leftRectangle = new Rectangle();
				leftRectangle.setTop(middle.getY());
				leftRectangle.setBottom(rect.getBottom());
				leftRectangle.setLeft(rect.getLeft());
				leftRectangle.setRight(rect.getRight());
				node.setLeft(buildTree(leftRectangle, new Line(Straight.VERTICAL, Direction.BOTTOM), points.subList(0, middleIndex)));

				Rectangle rightRectangle = new Rectangle();
				rightRectangle.setTop(rect.getTop());
				rightRectangle.setBottom(middle.getY());
				rightRectangle.setLeft(rect.getLeft());
				rightRectangle.setRight(rect.getRight());
				node.setRight(buildTree(rightRectangle, new Line(Straight.VERTICAL, Direction.TOP), points.subList(middleIndex+1, points.size())));

				return node;
			}
			else {
				Rectangle rightRectangle = new Rectangle();
				rightRectangle.setTop(middle.getY());
				rightRectangle.setBottom(rect.getBottom());
				rightRectangle.setLeft(rect.getLeft());
				rightRectangle.setRight(rect.getRight());
				node.setRight(buildTree(rightRectangle, new Line(Straight.VERTICAL, Direction.BOTTOM), points.subList(0, middleIndex)));

				Rectangle leftRectangle = new Rectangle();
				leftRectangle.setTop(rect.getTop());
				leftRectangle.setBottom(middle.getY());
				leftRectangle.setLeft(rect.getLeft());
				leftRectangle.setRight(rect.getRight());
				node.setLeft(buildTree(leftRectangle, new Line(Straight.VERTICAL, Direction.TOP), points.subList(middleIndex+1, points.size())));

				return node;
			}
		}
		else {
			points.sort(Comparator.comparingDouble(Point::getX));
			int middleIndex = (points.size()-1)/2;
			Point middle = points.get(middleIndex);
			line.setPoint(middle);
			Node node = new Node(rect, line);

			Rectangle leftRectangle = new Rectangle();
			leftRectangle.setTop(rect.getTop());
			leftRectangle.setBottom(rect.getBottom());
			leftRectangle.setLeft(rect.getLeft());
			leftRectangle.setRight(middle.getX());
			node.setLeft(buildTree(leftRectangle, new Line(Straight.HORIZONTAL, Direction.LEFT), points.subList(0, middleIndex)));

			Rectangle rightRectangle = new Rectangle();
			rightRectangle.setTop(rect.getTop());
			rightRectangle.setBottom(rect.getBottom());
			rightRectangle.setLeft(middle.getX());
			rightRectangle.setRight(rect.getRight());
			node.setRight(buildTree(rightRectangle, new Line(Straight.HORIZONTAL, Direction.RIGHT), points.subList(middleIndex+1, points.size())));

			return node;
		}
	}

	public void print(Node n) {
		if(n != null) {
			print(n.getLeft());
			System.out.println(n);
			print(n.getRight());
		}
	}

	public void printTreeToFile(Node root, File file) {
		if(root != null) {
			printTreeToFile(root.getLeft(), file);

			if(root.getLeft() != null) {
				try (FileWriter writer = new FileWriter(file, true)) {
					writer.write("\"" + root + "\"->\"" + root.getLeft() + "\";\n");
					writer.flush();
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
			if(root.getRight() != null) {
				try (FileWriter writer = new FileWriter(file, true)) {
					writer.write("\"" + root + "\"->\"" + root.getRight() + "\";\n");
					writer.flush();
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}

			printTreeToFile(root.getRight(), file);
		}
	}

	public List<Point> findRegion(Node node, Region region) {
		List<Point> list = new ArrayList<>();

		if(node != null && node.getLine() != null) {
			if(node.getLine().getStraight() == Straight.VERTICAL) {
				if(Float.compare(node.getLine().getPoint().getX(), region.getBottomLeft().getX()) < 0) {
					list.addAll(findRegion(node.getRight(), region));
				}
				else if(Float.compare(node.getLine().getPoint().getX(), region.getTopRight().getX()) > 0) {
					list.addAll(findRegion(node.getLeft(), region));
				}
				else {
					if(region.isPointInside(node.getLine().getPoint())) {
						list.add(node.getLine().getPoint());
					}
					list.addAll(findRegion(node.getRight(), region));
					list.addAll(findRegion(node.getLeft(), region));
				}
			}
			else if(node.getLine().getStraight() == Straight.HORIZONTAL){
				if(node.getLine().getDirection() == Direction.RIGHT) {
					if(Float.compare(node.getLine().getPoint().getY(), region.getBottomLeft().getY()) < 0) {
						list.addAll(findRegion(node.getLeft(), region));
					}
					else if(Float.compare(node.getLine().getPoint().getY(), region.getTopRight().getY()) > 0) {
						list.addAll(findRegion(node.getRight(), region));
					}
					else {
						if(region.isPointInside(node.getLine().getPoint())) {
							list.add(node.getLine().getPoint());
						}
						list.addAll(findRegion(node.getRight(), region));
						list.addAll(findRegion(node.getLeft(), region));
					}
				}
				else if(node.getLine().getDirection() == Direction.LEFT){
					if(Float.compare(node.getLine().getPoint().getY(), region.getBottomLeft().getY()) < 0) {
						list.addAll(findRegion(node.getRight(), region));
					}
					else if(Float.compare(node.getLine().getPoint().getY(), region.getTopRight().getY()) > 0) {
						list.addAll(findRegion(node.getLeft(), region));
					}
					else {
						if(region.isPointInside(node.getLine().getPoint())) {
							list.add(node.getLine().getPoint());
						}
						list.addAll(findRegion(node.getRight(), region));
						list.addAll(findRegion(node.getLeft(), region));
					}
				}
			}
		}
		return list;
	}

	public void graphviz() throws IOException {
		File file = new File("graphviz.txt");
		boolean newFile = file.createNewFile();
		try(FileWriter writer = new FileWriter(file))
		{
			writer.write("digraph G {\n");
			writer.flush();
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
		//printNodeShapeToFile(myRoot, file);
		printTreeToFile(root, file);
		try(FileWriter writer = new FileWriter(file, true))
		{
			writer.write("}");
			writer.flush();
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}
}
