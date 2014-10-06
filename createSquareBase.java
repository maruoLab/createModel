import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;

public class createSquareBase{
	public static void main(String[] args){
		int width = 0, height = 0, lineInterval = 0, baseDistanse = 0;

		width = getInputData("width");
		height = getInputData("height");
		lineInterval = getInputData("lineInterval");
		/* baseDistanse = getInputData("baseDistanse"); */

		try{
			
			File file = createFile("model", "csv" );

			if(checkBeforeWritefile(file)){
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

				pw.println(createSpiralScanPattern(width, height, lineInterval, baseDistanse));
				pw.close();
			}else{
				System.out.println("ファイルに書き込めません");
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}

	public static File createFile(String filename, String fileFormat){

		File newFile = new File("./" + filename + ".000." + fileFormat);
		
		try{
			if(newFile.createNewFile()){
				System.out.println("ファイルの作成に成功");
			}else{
				System.out.println("ファイルの作成に失敗");
			}
		}catch(IOException e){
			System.out.println(e);
			System.exit(-1);
		}

		return newFile;
	}

	public static int getInputData(String strData){

		int inputValue = 0;
		System.out.print(strData + " : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try{
			strData = br.readLine();
			inputValue = Integer.parseInt(strData);
		}catch(NumberFormatException e){
			System.out.println("数字を入れて下さい");
			System.exit(-1);
		}catch(IOException e){
			System.out.println("入力エラー:" + e.getMessage());
			System.exit(-1);
		}
		return inputValue;
	}

	//private
	private static boolean checkBeforeWritefile(File file){
		if(file.exists()){
			if(file.isFile() && file.canWrite()){
				return true;
			}
		}
		return false;
	}

	public static String createSpiralScanPattern(int width, int height, int lineInterval, int baseDistanse){

		String scanPattern = new String();
		scanPattern += "0,0,-1\n";
		ArrayList <String>tmpArr = new ArrayList<String>();
		System.out.println("h:"+height+"w:"+width+"i:"+lineInterval+"d:"+baseDistanse);

		//長方形作製
		for(int i=0; width-2*lineInterval*i >= 0;i++){
            String rect = new String();

            rect +=  String.valueOf(- width / 2.0 + lineInterval * i) + ',' + String.valueOf(- height / 2.0 + lineInterval * i) + ",1"+ '\n';
            rect +=  String.valueOf(  width / 2.0 - lineInterval * i) + ',' + String.valueOf(- height / 2.0 +  lineInterval * i) + ",1"+'\n';
            rect +=  String.valueOf(  width / 2.0 - lineInterval * i) + ',' + String.valueOf(  height / 2.0 - lineInterval * i) + ",1"+'\n';
			rect +=  String.valueOf(- width / 2.0 + lineInterval * i) + ',' + String.valueOf(  height / 2.0 - lineInterval * i) + ",1"+'\n';
            rect +=  String.valueOf(- width / 2.0 + lineInterval * i) + ',' + String.valueOf(- height / 2.0 + lineInterval * i) + ",1"+ '\n';
			tmpArr.add(rect);

		}

		//渦巻きを反対にする
		for(int i=0;i<tmpArr.size();i++){
			scanPattern += tmpArr.get(tmpArr.size() - 1 - i);
		}

		System.out.println(scanPattern);
		
		return scanPattern;
		
	}

}

