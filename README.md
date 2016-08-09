# Fragments

Данные для ДЗ можно взять из тестового задания. Можно не грузить из сети, а положить файл в ассеты. А можно совместить с ДЗ по ContentProvider'ам, чтобы там клиент выполнял это ДЗ. 
Приложение должно отображать данные об исполнителях (или подобную инфу, взятую из любых источников ([тыц](http://www.programmableweb.com/apis/directory))).

* На телефоне в виде табов (ViewPager + SlidingTabLayout + FragmentStatePagerAdapter):
  * Заголовок таба - имя исполнителя;
  * Контент - фото + кнопка "Подробнее", которая открывает полную инфу об исполнителе во весь экран (отдельным фрагментом).
* На планшете: 
  * Cлева список в котором маленькое фото + имя;
  * Cправа - большое фото + кнопка "Подробнее", которая открывает полную инфу об исполнителе в диалоге (DialogFragment).

Примерный набросок, как это должно выглядеть: https://github.com/yamblz-native/fragments/blob/master/Homework.pdf

Любые дополнительные детали приветствуются.

# Done
https://github.com/artemohanjanyan/CPClient
https://github.com/artemohanjanyan/CPServer
