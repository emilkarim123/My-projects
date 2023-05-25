#pragma once

#include <SDL2/SDL.h>

namespace utils
{

	inline float hireTimeinSeconds()
	{
		float t = SDL_GetTicks();
		t *= 0.001f;
		return t;
	}

}; 