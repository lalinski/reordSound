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
			Toast.makeText(MainActivity.this, "û��SD��", Toast.LENGTH_LONG).show();
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
					/* ����¼���ļ� */
					/*mRecAudioFile = File.createTempFile(strTempFile, ".amr", mRecAudioPath);
					 ʵ����MediaRecorder���� 
					mMediaRecorder = new MediaRecorder();
					 ������˷� 
					mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					 ��������ļ��ĸ�ʽ 
					mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
					 ������Ƶ�ļ��ı��� 
					mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					 ��������ļ���·�� 
					mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
					 ׼�� 
					mMediaRecorder.prepare();
					 ��ʼ 
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
		/* ֹͣ��ť�¼����� */
		StopButton.setOnClickListener(new Button.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				if (mRecAudioFile != null)
				{
					/* ֹͣ¼�� */
					mMediaRecorder.stop();
					/* ��¼���ļ���ӵ�List�� */
					mMusicList.add(mRecAudioFile.getName());
					ArrayAdapter<String> musicList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mMusicList);
					setListAdapter(musicList);
					/* �ͷ�MediaRecorder */
					mMediaRecorder.release();
					mMediaRecorder = null;
				}
			}
		});
	}
	/* ����¼���ļ� */
	private void playMusic(File file)
	{
//		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		/* �����ļ����� */
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
	/* �����ǵ���б�ʱ�����ű���������� */
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		/* �õ���������ļ� */
		File playfile = new File(mRecAudioPath.getAbsolutePath() + File.separator + mMusicList.get(position));
		Toast.makeText(MainActivity.this, playfile.toString(), Toast.LENGTH_LONG).show();
		/* ���� */
		playMusic(playfile);
	}
	/* �����б� */
	public void musicList()
	{
		// ȡ��ָ��λ�õ��ļ�������ʾ�������б�
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

/* �����ļ����� */
class MusicFilter implements FilenameFilter
{
	public boolean accept(File dir, String name)
	{
		return (name.endsWith(".3pg"));
	}
}
