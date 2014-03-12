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
  
  void updatePosition(){
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
  
  void checkCollision(){
    float distance = PVector.dist(player.position, position);
    if (distance < (radius+player.radius)){
      isDead = true;
      score += 100*multiplyer;
      killStreak++;
    }
  }
  
  void display(){
    pushMatrix();
    translate(position.x, position.y);
    rotate(direction.heading());
    image(ship, -25, -25);
    popMatrix();
  }
}
