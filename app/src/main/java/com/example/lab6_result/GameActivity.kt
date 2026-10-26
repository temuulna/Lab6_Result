package com.example.lab6_result


import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
    var imageviewId = intArrayOf(
        R.id.image01, R.id.image02, R.id.image03,
        R.id.image04, R.id.image05, R.id.image06,
        R.id.image07, R.id.image08, R.id.image09
    )
    var framelayoutId = intArrayOf(
        R.id.frame_layout01, R.id.frame_layout02, R.id.frame_layout03,
        R.id.frame_layout04, R.id.frame_layout05, R.id.frame_layout06,
        R.id.frame_layout07, R.id.frame_layout08, R.id.frame_layout09
    )
    var ivObj: Array<ImageView?> = arrayOfNulls<ImageView>(9)
    var flayoutObj: Array<FrameLayout?> = arrayOfNulls<FrameLayout>(9)
    var imgArray: Array<ImageView?> = arrayOfNulls<ImageView>(9)  // 체크 표시 그림
    var selectNum = IntArray(9)   // 선택 여부 (1 = 선택)
    var mShape = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ⭐ 첫 화면에서 보낸 "shape" 값 받기
        mShape = intent.getIntExtra("shape", 0)

        val shapeObj: ImageView = findViewById(R.id.imageView)
        if (mShape == 1) shapeObj.setImageResource(R.drawable.circle)
        else if (mShape == 2) shapeObj.setImageResource(R.drawable.square)

        for (i in 0..8) {
            selectNum[i] = 0
            ivObj[i] = findViewById(imageviewId[i])
            flayoutObj[i] = findViewById(framelayoutId[i])
        }

        var num = 0
        for (i in 0..8) {
            ivObj[i]?.setOnClickListener {
                if (selectNum[i] == 1) {          // 이미 선택 → 해제
                    selectNum[i] = 0
                    num--
                    flayoutObj[i]?.removeView(imgArray[i])
                } else {                          // 새로 선택
                    if (num < 3) {
                        selectNum[i] = 1
                        num++
                        imgArray[i] = ImageView(this)
                        imgArray[i]?.setImageResource(R.drawable.ok_check)
                        flayoutObj[i]?.addView(imgArray[i])
                    } else {
                        Toast.makeText(this, "3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // ⭐ 결과보기: 선택 배열을 담아서 돌려주고 화면 닫기
        val btnObj: Button = findViewById(R.id.button)
        btnObj.setOnClickListener {
            intent.putExtra("select", selectNum)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}