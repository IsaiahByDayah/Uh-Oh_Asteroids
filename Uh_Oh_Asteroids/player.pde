class Player{
  PImage player;
  int playerSize;
  PVector position;
  PVector velocity;
  PVector direction;
  PVector force;
  float speed;
  float maxSpeed;
  float friction = 0.95;
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
  
  void updatePosition(){
    if(isAccelerating == true){
//      direction.x = 0;
//      direction.y = 0;
      if(isMovingRight == true){
        //direction.x += 0.5;
        force.x += 0.2;
      }
      if(isMovingLeft == true){
        //direction.x = -0.5;
        force.x -= 0.2;
      }
      if(isMovingUp == true){
        //direction.y = -0.5;
        force.y -= 0.2;
      }
      if(isMovingDown == true){
        //direction.y = 0.5;
        force.y += 0.2;
      }
      speed += 0.5;
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
    direction.rotate(0.05);
    
    if(position.x > width) position.x = 0;
    if(position.x < 0) position.x = width;
    if(position.y > height) position.y = 0;
    if(position.y < 0) position.y = height;
    checkSize();
  }
  
  void display(){
      pushMatrix();
      translate(position.x, position.y);
      rotate(direction.heading());
      //ellipse(0, 0, 80, 80);
      image(player, -50, -50);
      popMatrix();
  }
  
  void checkSize(){
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
