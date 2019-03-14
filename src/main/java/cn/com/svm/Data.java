/**
 * @Title: my_SVM.java
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年12月16日
 * @version 1.0
 */
package cn.com.svm;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
/**
 * @author Kevin
 *
 */
public class Data {
	
	
	private int maxLimit = 100;
	private int minLimit = -100;
	
	public void write(Function<Double, Double> function, String path, int count) throws IOException {
		FileWriter writer = new FileWriter(path);
		Random random = new Random();
		Set<Integer> cache = new HashSet<Integer>();
		IntStream.range(0, count).forEach(
				(i)-> {
						int num = random.nextInt(maxLimit - minLimit) + minLimit;
						if (!cache.contains(num)) {
							cache.add(num);
							try {
								writer.write(function.apply((double) num) + " 1:" + num + "\n");
							} catch (IOException e) {
								e.printStackTrace();
						}
					}
				}
			);
		writer.close();
	}
	public void createDataSet() throws IOException {
		Function<Double, Double> function = x -> x;
		write(function, "F:\\train.data", 210);
		write(function, "F:\\test.data", 90);
	}
	
	public static void main(String[] args) throws IOException {

			new Data().createDataSet();
		}
	
}


