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
    speed = 2.5;
    radius = 5;
    isDead = false;
    musicPlayer2.play(0);
  }
  
  void updatePosition(){
    position.add(PVector.mult(direction, speed));
    
    checkCollision();
  }
  
  void checkCollision(){
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
  
  void display(){
    pushMatrix();
    translate(position.x, position.y);
    ellipse(0, 0, radius*2, radius*2);
    popMatrix();
  }
}
