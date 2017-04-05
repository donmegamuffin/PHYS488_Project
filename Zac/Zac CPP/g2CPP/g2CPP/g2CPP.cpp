// g2CPP.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "DataTypes.h"
#include "Particle.h"
#include <chrono>

static auto pi = std::acos(-1);
static const double c = 299792458;
int main()
{
	double RingRadius = 7000.;
	double RingLength = (pi*2*RingRadius);
	double TestDouble[3] = { 0.,0.,0. };
	Vector *spawnLocation = new Vector(TestDouble);
	Rotation *spawnOrientation = new Rotation(TestDouble);
	Vector *spawnScale = new Vector(TestDouble);
	Transform *TestTransform = new Transform(*spawnLocation, *spawnOrientation, *spawnScale);
	auto start = std::chrono::high_resolution_clock::now();
	for(int n = 0; n < 100; n++)
	{
		Particle* Muon = new Particle(*TestTransform, 3.105, 3.103, 1, 0.106, 2.2e-6);
		double Distance = (Muon->getBeta()*Muon->getLifetime()*c);	///Gets distance travelled in m
		Rotation* DecayAngle = new Rotation(0, 0, (Distance / RingLength));
		Vector* DecayPosition = new Vector({ RingRadius*cos(DecayAngle->getYaw()),(RingRadius*sin(DecayAngle->getYaw())),0. });
		/*std::cout << DecayPosition->getX() << " , " << DecayPosition->getY() << std::endl;*/
		std::cout << Distance << std::endl;
		delete Muon;
		delete DecayAngle;
		delete DecayPosition;
	}
	auto finish = std::chrono::high_resolution_clock::now();
	std::cout << std::chrono::duration_cast<std::chrono::nanoseconds>(finish - start).count() << "ns\n";
	///{0,0,0} is top centre of ring

}

