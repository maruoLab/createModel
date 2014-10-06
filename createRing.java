import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.lang.Math;

public class createRing{
	public static void main(String[] args){
		int rotationNumber = 0, radius = 0, scanDistance = 0, deltaAngle = 0;

		radius = getInputData("半径");
		scanDistance = getInputData("スキャンディスタンス");
		deltaAngle = getInputData("分割角度");
		rotationNumber = getInputData("周回数");

		try{
			
			File file = createFile("model", "csv" );

			if(checkBeforeWritefile(file)){
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

				pw.println(createRingScanPattern(radius, scanDistance, deltaAngle, rotationNumber));
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
			System.out.println("数字(0以上)を入れて下さい");
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

	public static String createRingScanPattern(int r, int d, int da, int n)
	{

		String scanPattern = new String();
		scanPattern += "0,0,-1\n";
		ArrayList <String>tmpArr = new ArrayList<String>();
		System.out.println("radius:"+ r +"スキャン幅:" + d +"分割角度:" + da +"周回数:" + n);

		for(int i=0;i<n;i++)
		{
			for(int j=0;j*da<=360;j++){

				double x = (r + d*i) * Math.sin(j*(double)da/180*Math.PI);
				double y = (r + d*i) * Math.cos(j*(double)da/180*Math.PI);
				if(Math.abs(x)<0.0000001)x=0;
				if(Math.abs(y)<0.0000001)y=0;
				x = Math.round(x);
				y = Math.round(y);
				/* System.out.println(i+"周目, 角度"+j*da); */
				/* System.out.println("x:"+x+", y:"+y); */
				scanPattern += String.valueOf((int)x) + ","+ String.valueOf((int)y)+",1\n";
			}

		}

		System.out.println(scanPattern);
		
		return scanPattern;
		
	}

}

