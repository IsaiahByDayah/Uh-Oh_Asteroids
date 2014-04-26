import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Uh_Oh_Asteroids extends PApplet {

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


AudioPlayer musicPlayer;
Minim minim;

AudioPlayer musicPlayer2;
Minim minim2;

public void setup() {
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

public void resetGame(){
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

public boolean levelCleared(){
  boolean clear = true;
  for (int i = 0; i < ships.size(); i++){
    if(!ships.get(i).isDead){
      clear = false;
    }
  }
  return clear;
}

public void checkGame(){
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

public void loadScores(){
  highScoresText = loadStrings("highScores.txt");
  first = PApplet.parseInt(highScoresText[0]);
  second = PApplet.parseInt(highScoresText[1]);
  third = PApplet.parseInt(highScoresText[2]);
}

public void saveScores(){
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

public void draw(){
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

public void drawHighScores(){
  textAlign(CENTER);
  String firstString = "1st: " + first;
  text(firstString, 360, 200);
  
  String secondString = "2nd: " + second;
  text(secondString, 360, 250);
  
  String thirdString = "3rd: " + third;
  text(thirdString, 360, 300);
}

public void drawGUI(){
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

public void keyPressed() {
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

public void keyReleased() {
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
class Bullet{
  PVector position;
  float speed;
  PVector direction;
  float radius;
  boolean isDead;
  
  Bullet(PVector _p, PVector _d){
    position = _p;
    _d.rotate(-HALF_PI);
    direction = _d;
    //println("position: " + position);
    //println("direction: " + direction);
    speed = 2.5f;
    radius = 5;
    isDead = false;
    musicPlayer2.play(0);
  }
  
  public void updatePosition(){
    position.add(PVector.mult(direction, speed));
    
    checkCollision();
  }
  
  public void checkCollision(){
    float distance = PVector.dist(player.position, position);
    if (distance < (radius+player.radius)){
      isDead = true;
      lives--;
      if (lives != 0){
        multiplyer = 1;
        killStreak = 0;
      }
    }
  }
  
  public void display(){
    pushMatrix();
    translate(position.x, position.y);
    ellipse(0, 0, radius*2, radius*2);
    popMatrix();
  }
}
class Player{
  PImage player;
  int playerSize;
  PVector position;
  PVector velocity;
  PVector direction;
  PVector force;
  float speed;
  float maxSpeed;
  float friction = 0.95f;
  int rot;
  float radius;
  
  Player(float _s, float _m){
    position = new PVector(360, 240);
    velocity = new PVector(0, 0);
    direction = new PVector(1, 0);
    force = new PVector(0, 0);
    speed = _s;
    maxSpeed = _m;
    rot = 30;
    player = loadImage("Asteroid1.png");
    playerSize = 1;
    radius = 35/2;
  }
  
  public void updatePosition(){
    if(isAccelerating == true){
//      direction.x = 0;
//      direction.y = 0;
      if(isMovingRight == true){
        //direction.x += 0.5;
        force.x += 0.2f;
      }
      if(isMovingLeft == true){
        //direction.x = -0.5;
        force.x -= 0.2f;
      }
      if(isMovingUp == true){
        //direction.y = -0.5;
        force.y -= 0.2f;
      }
      if(isMovingDown == true){
        //direction.y = 0.5;
        force.y += 0.2f;
      }
      speed += 0.5f;
    } else {
      //direction.mult(friction);
      speed *= friction;
      force.mult(friction);
      
    }
    
    //velocity = PVector.mult(direction, speed);
    //velocity.limit(maxSpeed);
    //position.add(velocity);
    force.limit(maxSpeed);
    position.add(force);
    direction.rotate(0.05f);
    
    if(position.x > width) position.x = 0;
    if(position.x < 0) position.x = width;
    if(position.y > height) position.y = 0;
    if(position.y < 0) position.y = height;
    checkSize();
  }
  
  public void display(){
      pushMatrix();
      translate(position.x, position.y);
      rotate(direction.heading());
      //ellipse(0, 0, 80, 80);
      image(player, -50, -50);
      popMatrix();
  }
  
  public void checkSize(){
    if (playerSize == 1){
      player = loadImage("Asteroid1.png");
      radius = 35/2;
    } else if (playerSize == 2){
      player = loadImage("Asteroid2.png");
      radius = 55/2;
    } else if (playerSize == 3){
      player = loadImage("Asteroid3.png");
      radius = 70/2;
    } else if (playerSize == 4){
      player = loadImage("Asteroid4.png");
      radius = 80/2;
    } else if (playerSize == 5){
      player = loadImage("Asteroid5.png");
      radius = 100/2;
    }
    
    int numDead = 0;
    int thresh = floor(ships.size()/level);
    for(int i = 0; i < ships.size(); i++){
      if (ships.get(i).isDead){
        numDead++;
      }
    }
    int levels = floor(numDead/thresh);
    //println(levels);
    if (levels > 0 ){
      playerSize = levels;
    } else {
      playerSize = 1;
    }
    
  }
}
class Ship{
  PImage ship;
  PVector position;
  PVector speed;
  PVector direction;
  float radius;
  boolean isDead;
  int shootChance;
  int shootCount;

  Ship(){
    int x = round(random(0,1));
    if (x == 0){
      x = round(random(100,300));
    } else {
      x = round(random(420, 620));
    }
    int y = round(random(0,1));
    if (y == 0){
      y = round(random(0,100));
    } else {
      y = round(random(380, 480));
    }
    //position = new PVector(200,200);
    position = new PVector(x, y);
    
    speed = new PVector();
    //speed.x = random(0,0);
    //speed.y = random(0,0);
    speed.x = random(-2, 2);
    speed.y = random(-2, 2);
    
    direction = new PVector(0, 0);
    //PVector.fromAngle(0, direction);
    
    ship = loadImage("Uh-Oh!-Asteroid-Ship.png");
    radius = 25;
    isDead = false;
    
    shootChance = round(random(120,240));
    shootCount = 0;
  }
  
  public void updatePosition(){
      position.add(speed);
      
//        println("Velocity: " + velocity.x + " " + velocity.y);
//        println("Position: " + position.x + " " + position.y);

      
      //Another way of writing one-line if statements
      if(position.x > width) position.x = 0;
      if(position.x < 0) position.x = width;
      if(position.y > height) position.y = 0;
      if(position.y < 0) position.y = height;
      
      float changeX = position.x - player.position.x;
      float changeY = position.y - player.position.y;
      if (player.position.y < position.y){
        direction = PVector.fromAngle(-atan(changeX/changeY));
      } else {
        direction = PVector.fromAngle(PI-atan(changeX/changeY));
      }
      
      checkCollision();
      shootCount++;
  }
  
  public void checkCollision(){
    float distance = PVector.dist(player.position, position);
    if (distance < (radius+player.radius)){
      isDead = true;
      score += 100*multiplyer;
      killStreak++;
    }
  }
  
  public void display(){
    pushMatrix();
    translate(position.x, position.y);
    rotate(direction.heading());
    image(ship, -25, -25);
    popMatrix();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Uh_Oh_Asteroids" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
