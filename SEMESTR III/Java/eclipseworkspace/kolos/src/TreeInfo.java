
import java.util.regex.*;
import java.io.*;
import java.util.*;

public class TreeInfo implements Iterable<File>{
	public List<File> files = new ArrayList<File>;
	public list<File> dirs = new ArrayList<File>;
	
	public Iterator<File> iterator(){
		return files.iterator();
	}
	
	void addAll(TreeInfo other){
		files.addAll(other.files);
		dirs.addAll(other.dirs);
	}
	
	public String toString(){
		return "katalogi: " + PPrint.pformat(dirs) + "\\nnpliki: " + PPrint.pformat(files);
	}
	
	public static TreeInfo
	walk(File start, String regex){
		return recurseDirs(new File(start).regex);
	}
	public static TreeInfo
	walk(File start){
		return recurseDirs(start, regex);
	}
	public static TreeInfo walk(File start){
		return recurseDirs(start, ".*");
	}
	public static TreeInfo
	walk(File start, String regex){
		return recurseDirs(new File(start), ".*");
	}
	
	static TreeInfo recurseDirs(File startDir, String regex){
		TreeInfo result = new TreeInfo();
		for(File item: startDir.listFiles()){
			if(item..isDirectory()){
				result.irs.add(item);
				result.addAll(recurseDirs(item, regex));
			}else
				if(item.getName().matches(regex))
					result.files.add(item);
		}
		return result;
		
	}
}
