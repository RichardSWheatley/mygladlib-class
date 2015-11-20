import edu.duke.*;
import java.util.*;

public class MyGladLib {

  private Map<String,ArrayList<String>> multiMap = new HashMap<String,ArrayList<String>>();
  private Random myRandom;

	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "data";

	public MyGladLib(){
		initializeFromSource(dataSourceDirectory);

// 		initializeFromSource(dataSourceURL);
		myRandom = new Random();
	}

	public MyGladLib(String source){
		initializeFromSource(source);
		myRandom = new Random();
	}

	private void initializeFromSource(String source) {
		multiMap.put("adjective", readIt(source+"/adjective.txt"));
		multiMap.put("noun", readItreadIt(source+"/noun.txt"));
		multiMap.put("color", readItreadIt(source+"/color.txt"));
		multiMap.put("country", readItreadIt(source+"/country.txt"));
		multiMap.put("name", readItreadIt(source+"/name.txt"));
		multiMap.put("verb", readItreadIt(source+"/verb.txt"));
		multiMap.put("animal", readItreadIt(source+"/animal.txt"));
		multiMap.put("timeframe", readItreadIt(source+"/timeframe.txt"));
		multiMap.put("fruit", readItreadIt(source+"/fruit.txt"));
	}

	private String randomFrom(ArrayList<String> source){
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}

	private String getSubstitute(String label) {

		if (multiMap.keySet().contains(label)){
			return randomFrom(fruitList);
		}
		else if (label.equals("number")){
			return ""+myRandom.nextInt(50)+5;
		}
		return "**UNKNOWN**";
	}

	private String processWord(String w){
		int first = w.indexOf("<");
		int last = w.indexOf(">",first);
		if (first == -1 || last == -1){
			return w;
		}
		String prefix = w.substring(0,first);
		String suffix = w.substring(last+1);
		String sub = getSubstitute(w.substring(first+1,last));
		return prefix+sub+suffix;
	}

	private void printOut(String s, int lineWidth){
		int charsWritten = 0;
		for(String w : s.split("\\s+")){
			if (charsWritten + w.length() > lineWidth){
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w+" ");
			charsWritten += w.length() + 1;
		}
	}

	private String fromTemplate(String source){
		String story = "";
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		return story;
	}

	private ArrayList<String> readIt(String source){
		ArrayList<String> list = new ArrayList<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		return list;
	}

	public void makeStory(){
	    System.out.println("\n");
		String story = fromTemplate("data/madtemplate3.txt");
		printOut(story, 60);
	}



}
