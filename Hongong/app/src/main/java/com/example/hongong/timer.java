package com.example.hongong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class timer extends MainActivity {
    Chronometer timer;
    Button startB, resetB, stopB, saveB;
    TextView timeTV, tvTime;
    long studytime, hour, min, sec, allhour, allmin, allsec;
    View timersave;
    int alltime, beforetime;
    String alllog = "";
    MediaPlayer music;
    SeekBar playbar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("Hongong");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        timer = (Chronometer) findViewById(R.id.timer);
        startB = (Button) findViewById(R.id.startB);
        resetB = (Button) findViewById(R.id.resetB);
        stopB = (Button) findViewById(R.id.stopB);
        timeTV = (TextView) findViewById(R.id.timeTV);
        saveB = (Button) findViewById(R.id.saveB);
        tvTime = (TextView) findViewById(R.id.tvTime);
        playbar = (SeekBar) findViewById(R.id.playbar);

        timer.setTextColor(Color.GRAY);

        timer.setBase(SystemClock.elapsedRealtime());

        final Switch musicSW = (Switch) findViewById(R.id.musicSW);

        musicSW.setOnClickListener(new View.OnClickListener() {  // 스위치 눌리면 음악 재생
            @Override
            public void onClick(View view) {
                if(musicSW.isChecked() == true){
                    playMusic();
                } else{
                    stopMusic();
                }
            }
        });

        playbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {  // 프로그레스 바 움직여서 음악 조정
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    music.seekTo(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {  }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  }
        });

        startB.setOnClickListener(new View.OnClickListener() {  // 시작 버튼 누르면 타이머 시작
            @Override
            public void onClick(View view) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                timer.setTextColor(Color.BLACK);
            }
        });

        stopB.setOnClickListener(new View.OnClickListener() {  // 정지 버튼 누르면 타이머 정지하고 대화상자에 입력받기
            @Override
            public void onClick(View view) {
                timer.stop();
                timer.setTextColor(Color.BLUE);

                studytime = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000);  // 밀리초이므로 계산을 위해 1000으로 나누기
                timesave((int)studytime);
            }
        });

        resetB.setOnClickListener(new View.OnClickListener() {  // 리셋 버튼 누르면 값 리셋
            @Override
            public void onClick(View view) {
                timer.setBase(SystemClock.elapsedRealtime());
            }
        });

        saveB.setOnClickListener(new View.OnClickListener() {  // 저장 버튼 누르면 데이터베이스에 공부한 시간 저장
            @Override
            public void onClick(View view) {
                long now = System.currentTimeMillis();  // 현재 시간 이용해서 날짜 추출
                Date date = new Date(now);
                SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
                String day = d.format(date);

                setTime(day, alltime);
            }
        });
    }

    public void timesave(int studytime){  // 대화상자에 입력받고 시간 저장
        hour = studytime / (60 * 60);
        min = studytime % (60 * 60) / 60;
        sec = studytime % 60;

        timersave = (View) View.inflate(timer.this, R.layout.timersave, null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(timer.this);
        dlg.setTitle("공부 분야 지정");
        dlg.setView(timersave);

        String[] names = {"수학", "영어", "토익", "코딩", "C언어", "JAVA", "알고리즘", "안드로이드 스튜디오", "파이썬", "C++",
                "JS", "데이터베이스", "독서", "보안", "프로젝트", "C#", "앱 개발", "웹 개발"};

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names);
//        studyname.setAdapter(adapter);  // 자동완성 설정...인데 작동 안 함

        dlg.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {  // 확인 누르면 공부 분야와 시간이 textview에 출력되고 데이터베이스에 저장
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alltime += studytime;

                        TextView studyname = (TextView) timersave.findViewById(R.id.studyname);
                        String studynametext = "" + studyname.getText().toString();
                        String log = studynametext + " : " + hour + "시간" + min + "분" + sec + "초  " + alltime + "\n";

                        timeTV = (TextView) findViewById(R.id.timeTV);
                        timeTV.setText(alllog + log);

                        alllog += log;
                    }
                });
        dlg.show();
    }

    private void addTime(String date, int time){  // 공부 시간 기록
        timesaveR timesaveR = new timesaveR(time);
        databaseReference.child("study").child(date).setValue(timesaveR);
    }

    private void setTime(String day, int alltime){
        databaseReference.child("study").child(day).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  // 데이터를 새로 쓰기 위해 값 받아옴
                timesaveR result = snapshot.getValue(timesaveR.class);
//                try{
//                    beforetime = result.gettime();
//                    Log.v("v", "1");
//                } catch (Exception e){
                    beforetime = 0;
//                }
                addTime(day, beforetime + alltime);
                Toast.makeText(getApplicationContext(), "공부 시간을 정상적으로 업데이트하였습니다.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });
    }

    private void playMusic() {  // 음악 재생
        // closePlayer();
        music = MediaPlayer.create(timer.this, R.raw.music);
        music.start();
        Toast.makeText(this, "백색소음 재생 시작", Toast.LENGTH_SHORT).show();

        new Thread(){  // 스레드 진행
            SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
            public void run(){
                if(music == null) return;  // 빠져나가기
                playbar.setMax(music.getDuration());
                while(music.isPlaying()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playbar.setProgress(music.getCurrentPosition());
                            tvTime.setText("재생 시간 : " + timeFormat.format(music.getCurrentPosition()));
                        }
                    });
                    SystemClock.sleep(200);  // 0.2초마다 진행 상태 변경
                }
            }
        }.start();
    }

    private void stopMusic() {  // 음악 정지
        if(music != null && music.isPlaying()){
            music.stop();
            Toast.makeText(this, "백색소음 재생 중지", Toast.LENGTH_SHORT).show();

            playbar.setProgress(0);  // 프로그레스바 진행 시간 초기화
            tvTime.setText("재생 시간 : ");
        }
    }
}
