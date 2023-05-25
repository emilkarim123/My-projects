#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <iostream>
#include <vector>

#include "RenderWindow.hpp"
#include "Entity.hpp"
#include "Utils.hpp"

int main(int argc, char* args[])
{
	if (SDL_Init(SDL_INIT_VIDEO) > 0)
		std::cout << "HEY.. SDL_Init HAS FAILED. SDL_ERROR: " << SDL_GetError() << std::endl;

	if (!(IMG_Init(IMG_INIT_PNG)))
		std::cout << "IMG_init has failed. Error: " << SDL_GetError() << std::endl;

	RenderWindow window("GAME v1.0", 256, 240);

	SDL_Texture* groundTexture = window.loadTexture("res/gfx/tilemap.png");

	std::vector<Entity> entities = {Entity(Vector2f(0, 224), groundTexture),
    Entity(Vector2f(16, 224), groundTexture),Entity(Vector2f(32, 224), groundTexture),Entity(Vector2f(48, 224), groundTexture),Entity(Vector2f(62, 224), groundTexture), Entity(Vector2f(78, 224), groundTexture), Entity(Vector2f(94, 224), groundTexture), Entity(Vector2f(110, 224), groundTexture), Entity(Vector2f(126, 224), groundTexture), Entity(Vector2f(142, 224), groundTexture), Entity(Vector2f(158, 224), groundTexture), Entity(Vector2f(174, 224), groundTexture), Entity(Vector2f(190, 224), groundTexture), Entity(Vector2f(206, 224), groundTexture)};

	bool gameRunning = true;

	const float timeStep = 0.01;
	float accumulator = 0.0f;
	float currentTime = utils::hireTimeinSeconds(); 

	SDL_Event event;

	while (gameRunning)
	{
		int startTicks = SDL_GetTicks();
		float newTime = utils::hireTimeinSeconds(); 
		float frameTime = newTime - currentTime;

		currentTime = newTime;

		accumulator += frameTime;

		while(accumulator >= timeStep){
				// Get our controls and events
			while (SDL_PollEvent(&event))
			{
				if (event.type == SDL_QUIT)
					gameRunning = false;
			}
			accumulator -= timeStep;

		}
		const float alpha = accumulator/timeStep;

		window.clear();
		for (Entity& e : entities)
		{ 
			window.render(e);
		}
		window.display();

		int frameTicks = SDL_GetTicks() - startTicks; 
		if(frameTicks < 1000/ window.getRefreshRate()){
			SDL_Delay(1000/ window.getRefreshRate() -frameTicks);
		}
	}

	window.cleanUp();
	SDL_Quit();

	return 0;
}
