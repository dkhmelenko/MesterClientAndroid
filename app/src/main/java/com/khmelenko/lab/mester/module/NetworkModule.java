package com.khmelenko.lab.mester.module;

import com.khmelenko.lab.mester.activity.AddProjectActivity_;
import com.khmelenko.lab.mester.activity.AddStepActivity_;
import com.khmelenko.lab.mester.activity.AddTestcaseActivity_;
import com.khmelenko.lab.mester.activity.MainActivity_;
import com.khmelenko.lab.mester.activity.NewTestingActivity_;
import com.khmelenko.lab.mester.activity.ProjectManagementActivity_;
import com.khmelenko.lab.mester.activity.StepsActivity_;
import com.khmelenko.lab.mester.activity.TestActivity;
import com.khmelenko.lab.mester.activity.TestcasesActivity_;
import com.khmelenko.lab.mester.activity.TestingResultsActivity_;
import com.khmelenko.lab.mester.network.RestClient;
import com.khmelenko.lab.mester.network.retrofit.RestClientRetrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Specifies the module of the network layer
 *
 * @author Dmytro Khmelenko
 */
@Module(
        injects = {MainActivity_.class, AddProjectActivity_.class, TestcasesActivity_.class,
                AddTestcaseActivity_.class, StepsActivity_.class, AddStepActivity_.class,
                ProjectManagementActivity_.class, TestingResultsActivity_.class,
                NewTestingActivity_.class}
)
public class NetworkModule {

    @Provides
    @Singleton
    public RestClient provideRestClient() {
        return new RestClientRetrofit();
    }
}

