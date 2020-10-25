package application;

//把握したいインターフェースは、*を用しない事とする
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class Main extends Application {

	@Override
	public void start(Stage stage) {

		stage.show();
		Group group1 = new Group();                 //Group作成
		Scene scene1 = new Scene(group1, 640, 480); //Scene作成
		stage.setScene(scene1);                     //scene貼り付け

		MenuBar menuBar1 = new MenuBar();    //メニューバー作成
		menuBar1 .setPrefWidth(640);         //幅設定
		menuBar1 .setPrefHeight(30); 	     //高さ設定
		group1.getChildren().add(menuBar1);  //groupのコントロールリストにメニューバーを追加

		Menu menu1 = new Menu("ファイル");   //menuを作成
		menuBar1.getMenus().add(menu1);      //メニューバーにmenuを追加

		MenuItem[] menuItem = new MenuItem[2]; //menuアイテムを作成

		menuItem[0] = new MenuItem("開く");  //メニューアイテムを作成
		menu1.getItems().add(menuItem[0]);   //menuにメニューアイテム追加

		menuItem[1] = new MenuItem("保存");  //menuアイテムを作成
		menu1.getItems().add(menuItem[1]);   //menuにメニューアイテム追加

		TextArea textArea1 = new TextArea(); //TextArea作成
		textArea1.setLayoutY(30);            //Y座標を設定
		textArea1.setPrefColumnCount(100);   //列数を設定
		textArea1.setPrefRowCount(100);      //行数を設定
		group1.getChildren().add(textArea1); //groupに追加

		menuItem[0].setOnAction(e -> { 		   //ファイル開く処理
			try {
				FileChooser fileChooser = new FileChooser();   //FileChooserインスタンス化
				//拡張子フィルタ
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("txtファイル", "*.txt"),
						new FileChooser.ExtensionFilter("全てのファイル", "*.*")
						);

				File file = fileChooser.showOpenDialog(stage); //ダイアログ表示

				if(file != null) {
					//ファイルからすべての行を読み取る。ファイルから取得したバイトは、UTF-8文字でデコードされる。 
					final String lines = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining("\n"));
					textArea1.setText(lines); //テキストエリアに反映
					return;	//FileTnputStream未使用の為、リソース解放は不要と考えられる
				}
			} catch(IOException i) {  //入出力例外の発生通知シグナルが投げられるので、catchで補捉
				i.printStackTrace();
			}
		});
		menuItem[1].setOnAction(e -> {  //setOnAction関数で保存ボタンの処理			
			try {
				FileChooser fileChooser = new FileChooser(); //FileChooserインスタンス化

				//拡張子フィルタ
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("txtファイル", "*.txt"),
						new FileChooser.ExtensionFilter("全てのファイル", "*.*")
						);
				File file = fileChooser.showSaveDialog(stage); // ダイアログ表示
				FileOutputStream fos = new FileOutputStream(file);

				String text = textArea1.getText(); // テキスト領域の文字列を取得
				fos.write(text.getBytes()); // ファイルに保存 ※getBytes()でStringをバイトにし、結果を新規バイト配列に格納できる 
				fos.close(); // ファイルを閉じる
			}catch(IOException i) {
				i.printStackTrace();
			}
		});

	}
}
