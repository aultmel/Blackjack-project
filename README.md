# blackjack-project
My Blackjack game in a framework

This project uses three controllers (HomeController, PlayController, FreeplayController) to create a Blackjack game website. To start it up, navigate to localhost:8080.  
When the user starts it up, they will be directed to the login page. From there, they are able to either log in if they already have an account, click on a link 
to register, or play without an account. If they play without an account, they will have $100 (which resets every time the program is stopped and started again) and will  
make a bet and play. If they register/log in, they will be directed to their profile page, which greets them by name, shows them what level they are at and how close to 
the next level they are, and shows them their money. There is also a link to play the game. When they click that link, they will be prompted to select a level of 
gameplay and make a bet. If they do not put a bet in, try to bet more money than they have, or try to bet less than the minimum amount required based on gameplay, 
they cannot continue until these errors are fixed. Then they can play the game as normal. They have access to the rules from any page (except the log in or register page), 
and the rules will open in a new tab. This project uses a mySQL database to store player information, such as username, password, money, and level.
