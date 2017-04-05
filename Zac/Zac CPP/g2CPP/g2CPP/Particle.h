#pragma once
#include"DataTypes.h" 

class Particle
{
public:
	Transform particleTransform;

	Particle();
	Particle(Transform);
	/**Fully define the main parameters of the particle
	@param in_Trans is particle location, rotation, and scale
	@param in_Energy is particle's Energy in MeV
	@param in_Momentum is particle's Momentum in MeV
	@param in_Charge is particle's Charge in electron charges
	@param in_Mass is particle's Mass in MeV
	@param in_Lifetime is lifetime of the particle at rest
	*/
	Particle(Transform in_Trans, double in_Energy, double in_Momentum, double in_Charge, double in_Mass, double in_Lifetime);

	double getGamma();
	double getBeta();
	double getLifetime();

	double energy;
	double momentum;
	double charge;
	double mass;
	double restLifetime;
	

	virtual ~Particle();
private:
	double relativisticLifetime;
	double gamma;
	double beta;
	double actualLifetime;
};

