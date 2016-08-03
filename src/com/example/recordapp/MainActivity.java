package com.example.recordapp;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	private Button			StartButton;
	private Button			StopButton;
	private TextView tv;
	private File			mRecAudioFile;
	private File			mRecAudioPath;
	
	private MediaRecorder	mMediaRecorder;
	
	private List<String>	mMusicList	= new ArrayList<String>();
	
	private String			strTempFile	= "recaudio_";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StartButton = (Button) findViewById(R.id.StartButton);
		StopButton = (Button) findViewById(R.id.StopButton);
		
		tv = (TextView) findViewById(R.id.pty);
	/*	if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			
			mRecAudioPath = Environment.getExternalStorageDirectory();
			musicList();
		}
		else
		{
			Toast.makeText(MainActivity.this, "没有SD卡", Toast.LENGTH_LONG).show();
		}*/
		mRecAudioPath = this.getFilesDir();
		tv.setText(mRecAudioPath.toString()+ "fjdaklfjlakfj");
		System.out.println(mRecAudioPath.toString());
		musicList();
		StartButton.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				try
				{
					/* 创建录音文件 */
					/*mRecAudioFile = File.createTempFile(strTempFile, ".amr", mRecAudioPath);
					 实例化MediaRecorder对象 
					mMediaRecorder = new MediaRecorder();
					 设置麦克风 
					mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					 设置输出文件的格式 
					mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
					 设置音频文件的编码 
					mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					 设置输出文件的路径 
					mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
					 准备 
					mMediaRecorder.prepare();
					 开始 
					mMediaRecorder.start();*/
					 mRecAudioFile = File.createTempFile(strTempFile, ".3pg", mRecAudioPath);
					 mMediaRecorder = new MediaRecorder();
					 mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					 mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					 mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
					 mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				     try {
				    	 mMediaRecorder.prepare();
				     } catch (IOException e) {
				         e.getMessage();
				     }
				     mMediaRecorder.start();

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		/* 停止按钮事件监听 */
		StopButton.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				if (mRecAudioFile != null)
				{
					/* 停止录音 */
					mMediaRecorder.stop();
					/* 将录音文件添加到List中 */
					mMusicList.add(mRecAudioFile.getName());
					ArrayAdapter<String> musicList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mMusicList);
					setListAdapter(musicList);
					/* 释放MediaRecorder */
					mMediaRecorder.release();
					mMediaRecorder = null;
				}
			}
		});
	}
	/* 播放录音文件 */
	private void playMusic(File file)
	{
//		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		/* 设置文件类型 */
//		intent.setDataAndType(Uri.fromFile(file), "audio");
//		startActivity(intent);
		MediaPlayer mPlayer = new MediaPlayer();
		try{
			mPlayer.setDataSource(file.toString());
			mPlayer.prepare();
			mPlayer.start();
		}catch(IOException e){
			e.getMessage();
		}

	}
	@Override
	/* 当我们点击列表时，播放被点击的音乐 */
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		/* 得到被点击的文件 */
		File playfile = new File(mRecAudioPath.getAbsolutePath() + File.separator + mMusicList.get(position));
		Toast.makeText(MainActivity.this, playfile.toString(), Toast.LENGTH_LONG).show();
		/* 播放 */
		playMusic(playfile);
	}
	/* 播放列表 */
	public void musicList()
	{
		// 取得指定位置的文件设置显示到播放列表
		File home = mRecAudioPath;
		if (home.listFiles(new MusicFilter()).length > 0)
		{
			for (File file : home.listFiles(new MusicFilter()))
			{
				mMusicList.add(file.getName());
			}
			ArrayAdapter<String> musicList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mMusicList);
			setListAdapter(musicList);
		}
	}
}

/* 过滤文件类型 */
class MusicFilter implements FilenameFilter
{
	public boolean accept(File dir, String name)
	{
		return (name.endsWith(".3pg"));
	}
}
