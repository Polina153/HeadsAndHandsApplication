# Contact me https://t.me/PrettyCurl
# Заметки на полях
- В приложении 5 классов: 
  - MainActivity: точка входа в приложение и запуск игры, инициализация нужных классов и отображение UI
  - Creature, Monster, Player: базовый класс и наследники. В классе Player реализована дополнительная логика для восстановления здоровья
  - Dice: содержит код анимации и код расчета урона. Анимация и расчет урона никак не взаимосвязаны (см. пояснения ниже)
- Имплементация кубика является чисто декоративной и добавлена для визуализации процесса. Код взят из какого-то публичного аккаунта на Github и немного переработан. Все расчеты урона делаются независимо от анимации кубика. 
- ОБРАТИТЕ ВНИМАНИЕ! Есть некоторый набор TODO, которые ставились по ходу разработки и являются желательными изменениями в коде, но не обязательными (если следовать ТЗ).
- Пометка FIXME ставится там, где я пока не разобралась, но не стала тратить время и отложила решение проблемы на потом.