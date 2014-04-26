Player player;

int level;
PImage splash;
PImage press;
PImage over;
boolean isMovingRight = false;
boolean isMovingLeft = false;
boolean isMovingUp = false;
boolean isMovingDown = false;
boolean isAccelerating = false;
ArrayList<Ship> ships;
ArrayList<Bullet> bullets;
boolean levelSetup = false;
int score;
int first;
int second;
int third;
IntList highScores;
String[] highScoresText;
int multiplyer;
//int frameCounter = 0;
int lives;
int killStreak;
boolean scoreUpdated;
String ngp;
boolean showLastLevel;
String lastLevel;

import ddf.minim.*;
AudioPlayer musicPlayer;
Minim minim;

AudioPlayer musicPlayer2;
Minim minim2;

void setup() {
  size(720, 480, P2D);
  //size(displayWidth, displayHeight, P2D);
  player = new Player(1, 5);
  level = 0;
  score = 0;
  multiplyer = 1;
  lives = 3;
  killStreak = 0;
  scoreUpdated = false;
  showLastLevel = false;
  ngp = "";
  splash = loadImage("Uh-Oh!-Asteroid-Splash.png");
  press = loadImage("Uh-Oh!-Asteroid-Press.png");
  over = loadImage("Uh-Oh!-Asteroid-Over.png");
  minim = new Minim(this);
  musicPlayer = minim.loadFile("Blown Away - No Percussion.mp3", 2048);
  minim2 = new Minim(this);
  musicPlayer2 = minim.loadFile("151022__bubaproducer__laser-shot-silenced.wav", 2048);
  musicPlayer.play();
  musicPlayer.loop();
  loadScores();
}

void resetGame(){
  player = new Player(1, 5);
  score = 0;
  multiplyer = 1;
  lives = 3;
  killStreak = 0;
  levelSetup = false;
  scoreUpdated = false;
  showLastLevel = false;
  ngp = "";
  splash = loadImage("Uh-Oh!-Asteroid-Splash.png");
  press = loadImage("Uh-Oh!-Asteroid-Press.png");
  over = loadImage("Uh-Oh!-Asteroid-Over.png");
  musicPlayer.play(0);
}

boolean levelCleared(){
  boolean clear = true;
  for (int i = 0; i < ships.size(); i++){
    if(!ships.get(i).isDead){
      clear = false;
    }
  }
  return clear;
}

void checkGame(){
  if (lives <= 0){
    level = -1;
    showLastLevel = true;
    if (!scoreUpdated){
      saveScores();
      scoreUpdated = true;
    }
  }
  multiplyer = killStreak + 1;
}

void loadScores(){
  highScoresText = loadStrings("highScores.txt");
  first = int(highScoresText[0]);
  second = int(highScoresText[1]);
  third = int(highScoresText[2]);
}

void saveScores(){
  if(score > first){
    third = second;
    second = first;
    first = score;
  } else if(score > second && score <= first){
    third = second;
    second = score;
  } else if (score > third && score <= second){
    third = score;
  }
  
  highScoresText[0] = str(first);
  highScoresText[1] = str(second);
  highScoresText[2] = str(third);
  saveStrings("highScores.txt", highScoresText);
}

void draw(){
  background(0);
  //frameCounter++;
  
  //Lose Screen
  //Level -1
  if (level == -1){
    image(over, 0, 0);
    image(press, 0, 0);
    drawHighScores();
  }
  
  //Start Screen
  //Level 0
  if (level == 0){
    image(splash, 0, 0);
    image(press, 0, 0);
  }
  
  //Level 1
  if (level == 1){
    if(!levelSetup){
      bullets = new ArrayList<Bullet>();
      ships = new ArrayList<Ship>();
      Ship ship = new Ship();
      ships.add(ship);
      lastLevel = "" + level + ngp;
      levelSetup = true;
    }
    
    for (int i = 0; i < ships.size(); i++){
      if (!ships.get(i).isDead){
        ships.get(i).updatePosition();
        if(ships.get(i).shootCount >= ships.get(i).shootChance){
          Bullet b = new Bullet(ships.get(i).position.get(), ships.get(i).direction.get());
          bullets.add(b);
          ships.get(i).shootCount = 0;
        }
        ships.get(i).display();
      }
    }
    
    for (int i = 0; i < bullets.size(); i++){
       if (!bullets.get(i).isDead){
         bullets.get(i).updatePosition();
         bullets.get(i).display();
       }
    }
    
    player.updatePosition();
    player.display();
    if (levelCleared()){
      level = 2;
      levelSetup = false;
    }
  }
  //End Level 1
  
  //Level 2
  if (level == 2){
    if(!levelSetup){
      bullets = new ArrayList<Bullet>();
      ships = new ArrayList<Ship>();
      for (int i = 0; i < 3; i++){
        Ship ship = new Ship();
        ships.add(ship);
      }
      lastLevel = "" + level + ngp;
      levelSetup = true;
    }
    for (int i = 0; i < ships.size(); i++){
      if (!ships.get(i).isDead){
        ships.get(i).updatePosition();
        if(ships.get(i).shootCount >= ships.get(i).shootChance){
          Bullet b = new Bullet(ships.get(i).position.get(), ships.get(i).direction.get());
          bullets.add(b);
          ships.get(i).shootCount = 0;
        }
        ships.get(i).display();
      }
    }
    
    for (int i = 0; i < bullets.size(); i++){
       if (!bullets.get(i).isDead){
         bullets.get(i).updatePosition();
         bullets.get(i).display();
       }
    }
    
    player.updatePosition();
    player.display();
    if (levelCleared()){
      level = 3;
      levelSetup = false;
    }
  }
  //end Level 2
  
  //Level 3
  if (level == 3){
    if(!levelSetup){
      bullets = new ArrayList<Bullet>();
      ships = new ArrayList<Ship>();
      for (int i = 0; i < 7; i++){
        Ship ship = new Ship();
        ships.add(ship);
      }
      lastLevel = "" + level + ngp;
      levelSetup = true;
    }
    for (int i = 0; i < ships.size(); i++){
      if (!ships.get(i).isDead){
        ships.get(i).updatePosition();
        if(ships.get(i).shootCount >= ships.get(i).shootChance){
          Bullet b = new Bullet(ships.get(i).position.get(), ships.get(i).direction.get());
          bullets.add(b);
          ships.get(i).shootCount = 0;
        }
        ships.get(i).display();
      }
    }
    
    for (int i = 0; i < bullets.size(); i++){
       if (!bullets.get(i).isDead){
         bullets.get(i).updatePosition();
         bullets.get(i).display();
       }
    }
    
    player.updatePosition();
    player.display();
    if (levelCleared()){
      //currently just restartes from level 1 (New Game +)
      level = 4;
      levelSetup = false;
    }
  }
  //end Level 3
  
  //Level 4
  if (level == 4){
    if(!levelSetup){
      bullets = new ArrayList<Bullet>();
      ships = new ArrayList<Ship>();
      for (int i = 0; i < 13; i++){
        Ship ship = new Ship();
        ships.add(ship);
      }
      lastLevel = "" + level + ngp;
      levelSetup = true;
    }
    for (int i = 0; i < ships.size(); i++){
      if (!ships.get(i).isDead){
        ships.get(i).updatePosition();
        if(ships.get(i).shootCount >= ships.get(i).shootChance){
          Bullet b = new Bullet(ships.get(i).position.get(), ships.get(i).direction.get());
          bullets.add(b);
          ships.get(i).shootCount = 0;
        }
        ships.get(i).display();
      }
    }
    
    for (int i = 0; i < bullets.size(); i++){
       if (!bullets.get(i).isDead){
         bullets.get(i).updatePosition();
         bullets.get(i).display();
       }
    }
    
    player.updatePosition();
    player.display();
    if (levelCleared()){
      //currently just restartes from level 1 (New Game +)
      level = 1;
      levelSetup = false;
      ngp += "+";
      lives += 1;
    }
  }
  //end Level 4
  
  drawGUI();
  checkGame();
//end Draw  
}

void drawHighScores(){
  textAlign(CENTER);
  String firstString = "1st: " + first;
  text(firstString, 360, 200);
  
  String secondString = "2nd: " + second;
  text(secondString, 360, 250);
  
  String thirdString = "3rd: " + third;
  text(thirdString, 360, 300);
}

void drawGUI(){
  textAlign(LEFT);
  String scoreString = "Score: " + score;
  text(scoreString, 50, 25);
  
  String livesString = "Lives: " + lives;
  text(livesString, 200, 25);
  
  String levelString = "Level: " + level + ngp;
  if(level < 1){
    levelString = "Level: 1";
  }
  if(showLastLevel){
    levelString = "Level: " + lastLevel;
  }
  text(levelString, 400, 25);
  
  String multiplyerString = "Multiplyer: " + multiplyer + "x";
  text(multiplyerString, 600, 25);
}

void keyPressed() {
  if(keyCode == RIGHT) {
      isMovingRight = true;
      isAccelerating = true;
  } 
  if(keyCode == LEFT) {
      isMovingLeft = true;
      isAccelerating = true;
  } 
  if(keyCode == UP) {
      isMovingUp = true;
      isAccelerating = true;
  } 
  if(keyCode == DOWN) {
      isMovingDown = true;
      isAccelerating = true;
  } 
  if (key == 's' || key == 'S'){
    String imageName = "Uh-Oh Asteroids - Screenshot.jpg";
    save(imageName);
  }
}

void keyReleased() {
  if(keyCode == RIGHT) {
      isMovingRight = false;
      isAccelerating = false;
  } 
  if(keyCode == LEFT) {
      isMovingLeft = false;
      isAccelerating = false;
  } 
  if(keyCode == UP) {
      isMovingUp = false;
      isAccelerating = false;
  } 
  if(keyCode == DOWN) {
      isMovingDown = false;
      isAccelerating = false;
  }
  if(keyCode == ENTER) {
      if(level == 0){
        level = 1;
      } else if(level == -1){
        resetGame();
        level = 0;
      }
  }
}
