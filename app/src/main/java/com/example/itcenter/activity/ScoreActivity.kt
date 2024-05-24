package com.example.itcenter.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.itcenter.R
import com.example.itcenter.databinding.ActivityScoreBinding
import com.example.itcenter.model.QuestionModel

class ScoreActivity : AppCompatActivity() {
    var score: Int = 0
    lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        score=intent.getIntExtra("Score",0)
        val txt = "${score}%"
        binding.score.text=txt
        binding.backMain.setOnClickListener {
                startActivity(Intent(this@ScoreActivity,QuizStartActivity::class.java))
                finish()
        }
        binding.refresh.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putParcelableArrayListExtra("list",ArrayList(questionList()))
            it.context.startActivity(intent)
        }
    }
    private fun questionList(): MutableList<QuestionModel> {
        val question: MutableList<QuestionModel> = mutableListOf()
        question.add(
            QuestionModel(
                1, "Kompyuter nima?",
                "Malumotlarga ishlov beruvchi elektron qurilma.",
                "Rasimlar bilan ishlovchi qurilma",
                "Internet bilan bog’lovchi qurilma",
                "Hamma javob noto’g’ri",
                "a",
                5,
                "q_1",
                null
            )
        )
        question.add(
            QuestionModel(
                2, "Microsoft Excel – bu?",
                "Elektron jadval muharriri",
                "Matn muharriri",
                "Elektron pochta",
                "Malumotlar bazasi",
                "a",
                5,
                "q_2",
                null
            )
        )
        question.add(
            QuestionModel(
                3, "Microsoft Word – bu?",
                "Elektron jadval muharriri",
                "Matn muharriri",
                "Elektron pochta",
                "Malumotlar bazasi",
                "b",
                5,
                "q_3",
                null
            )
        )
        question.add(
            QuestionModel(
                4, "Klaviaturada ctrl + c tugmalari baravar bosilganda qanday jarayon sodir bo’ladi?",
                "Belgilangan matn yoki fayldan nusxa oladi",
                "Dastur yopiladi",
                "Nusxa ko’chirilgan fayl yoki matn ko’rsatilgan joyga joyga qo’yadi",
                "Faylni saqlaydi",
                "a",
                5,
                "q_4",
                null
            )
        )
        question.add(
            QuestionModel(
                5, "Klaviaturada Alt + Shift tugmalari qanday vazifani bajaradi?",
                "Sichqoncha ko’rsatkichini o’zgartiradi",
                "Xujjat masshtabi o’zgaradi",
                "Yozuv tilini o’zgartirish",
                "Kompyuter parametriga kiradi",
                "c",
                5,
                "q_5",
                null
            )
        )
        question.add(
            QuestionModel(
                6, "Klaviaturaning qaysi tugmalar birikmasi yordamida obyektlarni chap tomonga o’chirish mumkin?",
                "End",
                "F8",
                "Delete",
                "Backspace",
                "d",
                5,
                "q_6",
                null
            )
        )
        question.add(
            QuestionModel(
                7, "Qaysi tugmalar birikmasi yordamida hujjatni chop etish buyrug’i beriladi?",
                "Alt + P",
                "Ctrl + Shift + P",
                "Alt + F4",
                "Ctrl + P",
                "d",
                5,
                "q_7",
                null
            )
        )
        question.add(
            QuestionModel(
                8, "Excel dasturida nechta qator bor?",
                "1,000,528",
                "1,047,000",
                "1,048,576",
                "1,876,000",
                "d",
                5,
                "q_8",
                null
            )
        )
        question.add(
            QuestionModel(
                9, "Klaviaturada Ctrl + V tugmalari baravar bosilganda qanday jarayon sodir bo’ladi?",
                "Belgilangan matn yoki fayldan nusxa oladi",
                "Dastur yopiladi",
                "Nusxa ko’chirilgan fayl yoki matn ko’rsatilgan joyga joyga qo’yadi",
                "Faylni saqlaydi",
                "c",
                5,
                "q_9",
                null
            )
        )
        question.add(
            QuestionModel(
                10, "Microsoft Word dasturiga fayl orqali kirish tartibi to’g’ri ko’rsatilgan javobni toping.",
                "Sichqonchaning chap tug. > Yaratish> Dokument Word.",
                "Sichqonchaning o’ng tug. > Haqida> Dokument Ms Word.",
                "Sichqonchaning o’ng tug. > Yaratish> Dokument Ms Word.",
                "Sichqonchaning chap tug. > Haqida> Dokument Ms Word.",
                "c",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                11, "Klaviaturaning Alt + F4 tugmalari qanday vazifani bajaradi?",
                "Dasturni ishini tugatadi.",
                "Dasturni pastki panelga olib qo’yish.",
                "Dasturni parametriga kiradi.",
                "Dasturni rangini o’zgartiradi.",
                "a",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                12, "Microsoft Office dasturlari tarkibiga kiruvchi dasturlarni toping.",
                "Word, Excel, Telegram.",
                "PowerPoint, Word, Excel,",
                "Excel, Google, PowerPoint",
                "Word, You tube, Excel.",
                "b",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                13, "Ctrl + H kombinatsiyasi nima vazifani bajaradi?",
                "Butun matnda biror bir so’z yoki yoki jumlani topib uning o’rniga boshqa so’z yoki jumla bilan almashtirish",
                "Xotirada joylashgan matn qismini qo’yish",
                "Butun matndan biror bir so’z yoki jumlani izlash",
                "Tanlab olingan matn qismini o’chirish",
                "a",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                14, "Klaviatura nima?",
                "Malumotlarni saqlash uchun kerak bo’ladigan qurilma",
                "Foydalanuvchi tomonidan malumotlarni kompyuterga kiritish qurilmasi",
                "Protsessordagi tasvirni ko’rish uchun kerak bo’ladigan qurilma",
                "Musiqa eshitishga moslangan qurilma",
                "b",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                15, "Microsoft Office dasturlarida biror so’zni qidirish uchun nimalar qilinadi?",
                "Ctrl + A yoki Asosiy bo’limdagi qidiruv oynasi orqali",
                "Ctrl + B yoki Joylash bo’limidagi qidiruv oynasi orqali",
                "Ctrl + F yoki Asosiy bo’limidagi qidiruv oynasi orqali",
                "Ctrl + P yoki Joylash bo’limidagi qidiruv oynasi orqali.",
                "c",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                16, "Excel dasturida nechta ustun bor?",
                "15,056",
                "16,384",
                "15,370",
                "16,140",
                "b",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                17, "Excel dasturida bir nechta ustunni bitta qilib qo’yish uchun qaysi buyruq bajariladi?",
                "Asosiy bo’limidagi Almashtirish matni orqali",
                "Joylash bo’limidagi Belgilar orqali",
                "Asosiy bo’limidagi Birlashtirish va markazga joylash orqali",
                "Joylash bo’limidagi bo’sh ro’yhat orqali",
                "c",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                18, "Word dasturida ramka qanday qo’yiladi?",
                "Asosiy bo’limidagi qog’oz chegarasi orqali",
                "Dizayn bo’limidagi qog’oz chegarasi orqali",
                "Maket bo’limidagi qog’oz chegarasi orqali",
                "Bilmayman",
                "b",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                19, "Kompyuterni to’liq o’chirish uchun nima qilish kerak?",
                "Pusk menyusidagi Ishni tugatish tugmasi orqali",
                "Ishchi stolidagi Ishni tugatish tugmasi orqali",
                "Alt + F4",
                "Alt + F5",
                "a",
                5,
                "q_10",
                null
            )
        )
        question.add(
            QuestionModel(
                20, "Papka ochish uchun nima qilish kerak?",
                "Sichqonchaning chap tomonini bosib yaratish bo’limiga kirib papka tanlanadi",
                "Sichqonchaning o’ng tugmasini bosib yaratish bo’limiga kirib papka tanlanadi",
                "Sichqonchaning chap va o’ng tugmasini birdaniga bosib yaratish bo’limiga kirib papka tanlanadi",
                "Bilmayman",
                "a",
                5,
                "q_10",
                null
            )
        )
        return question
    }

}