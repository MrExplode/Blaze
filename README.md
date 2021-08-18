# Blaze
Lightweight animation library made mainly for OpenGL usage

## Installation
Maven:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.MrExplode</groupId>
    <artifactId>Blaze</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```
Gradle:
```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.MrExplode:Blaze:1.0.1-SNAPSHOT'
}
```

## Building
You can build the project by running `./gradlew build`

## Usage
Create an `Animator` instance, and update it in every render loop
```java
Animator animator = new Animator();

//update in render loop
animator.update(partialTicks);
```
Create and use animations
```java
Animation animation1 = animator.create(Ease.LINEAR);
Animation animation2 = animator.create(Ease.SINE_IN_OUT, RunType.BOUNCE, Speed.MEDIUM);

animation1.start();

//use animation value in render call
//...
double animValue = animation1.getValue();
//eg. translate the matrix
GL11.glTranslate2d(x, y + animValue);
//...
```
You can also add a `Runnable` that runs at the end of the task
```java
//add to an existing animation
animation.onFinish(runnable);
//add during creation
Animation anim = animator.create(Ease.LINEAR).onFinish(runnable);
```

## License
```
    Blaze
    Copyright (C) 2021  SunStorm (aka. MrExplode)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
```