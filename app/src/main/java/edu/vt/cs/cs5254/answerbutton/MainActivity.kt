package edu.vt.cs.cs5254.answerbutton

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	private val defaultButtonColor = "#00a2ff"
	private val selectedButtonColor = "#cb297b"

	private lateinit var questionTextView: TextView
	private lateinit var answerButtonList: List<Button>
	private lateinit var disableButton: Button
	private lateinit var resetButton: Button

	private val answerList = listOf(
		Answer(R.string.australia_answer_brisbane, false),
		Answer(R.string.australia_answer_canberra, true),
		Answer(R.string.australia_answer_perth, false),
		Answer(R.string.australia_answer_sidney, false)
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// initialize views
		questionTextView = findViewById(R.id.question_text_view)
		answerButtonList = listOf(
			findViewById(R.id.answer_0_button),
			findViewById(R.id.answer_1_button),
			findViewById(R.id.answer_2_button),
			findViewById(R.id.answer_3_button)
		)
		disableButton = findViewById(R.id.disable_button)
		resetButton = findViewById(R.id.reset_button)

		// set text in views
		questionTextView.setText(R.string.australia_question)
		answerButtonList.forEachIndexed { index, btn ->
			btn.setText(answerList[index].textResId) }

		disableButton.setText(R.string.disable_button_text)
		resetButton.setText(R.string.reset_button_text)

		// attach listeners
		answerButtonList.forEachIndexed { index, answerButton ->
			answerButton.setOnClickListener {
				processAnswerButtonClick(index)
			}
		}
		disableButton.setOnClickListener {
			processDisableButtonClick()
		}
		resetButton.setOnClickListener {
			processResetButtonClick()
		}

		refreshView()
	}

	private fun processAnswerButtonClick(answerIndex: Int) {

		// deselect all buttons
		answerList.forEach {
			it.isSelected = false
		}

		// select clicked button
		answerList[answerIndex].isSelected = true

		// refresh the view
		refreshView()
	}

	private fun processDisableButtonClick() {
		// disable the first two incorrect answers (even if one is selected)
		var count = 0
		for (answer in answerList) {
			if (!answer.isCorrect) {
				answer.isEnabled = false
				answer.isSelected = false // deselect when answer is disabled
				count++
				if (count == 2) {
					break
				}
			}
		}
		disableButton.isEnabled = false
		refreshView()
	}

	private fun processResetButtonClick() {
		for (answer in answerList) {
			answer.isEnabled = true
			answer.isSelected = false
		}
		disableButton.isEnabled = true
		refreshView()
	}

	private fun refreshView() {
		answerList.forEachIndexed { index, answer ->
			val button = answerButtonList[index]
			if (answer.isEnabled) {
				button.isEnabled = true
				val colorString = if (answer.isSelected) selectedButtonColor else defaultButtonColor
				setButtonColor(button, colorString)
			} else {
				button.isEnabled = false
				setButtonColor(button, defaultButtonColor)
				button.alpha = .5f
			}
		}
	}

	private fun setButtonColor(button: Button, colorString: String) {
		button.backgroundTintList =
			ColorStateList.valueOf(Color.parseColor(colorString))
		button.setTextColor(Color.WHITE)
		button.alpha = 1f
	}
}
