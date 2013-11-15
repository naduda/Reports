package ua.pr.test;

public class Test3 {

	public static void main(String[] args) {

		System.out.println(getFullPath("d:/1.txt"));
		System.out.println(getFullPath("./1.txt"));
		System.out.println(getFullPath("../../1.txt"));
		System.out.println(getFullPath(""));
	}

	public static String getFullPath(String path) {
		path = path.replace("\\", "/");
		String res = "";
		if (path.substring(0,1).equals(".")) {
			if (path.substring(0, 2).equals("./")) {
				res = System.getProperty("user.dir") + "/" + path.subSequence(2, path.length());
			} else if (path.substring(0, 3).equals("../")) {
				String rPath = path;
				String lPath = System.getProperty("user.dir").replace("\\", "/");
				while (rPath.substring(0, 3).equals("../")) {
					lPath = lPath.substring(0, lPath.lastIndexOf("/"));
					rPath = rPath.substring(rPath.indexOf("/") + 1);
				}
				res = lPath + "/" + rPath;
			}
		} else {
			res = path;
		}
		
		return res.replace("\\", "/");
	}
}
