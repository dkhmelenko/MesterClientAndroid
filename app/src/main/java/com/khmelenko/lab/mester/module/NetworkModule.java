package com.khmelenko.lab.mester.module;

import com.khmelenko.lab.mester.activity.MainActivity_;
import com.khmelenko.lab.mester.activity.management.AddProjectActivity_;
import com.khmelenko.lab.mester.activity.management.AddStepActivity_;
import com.khmelenko.lab.mester.activity.management.AddTestcaseActivity_;
import com.khmelenko.lab.mester.activity.management.ProjectManagementActivity_;
import com.khmelenko.lab.mester.activity.management.StepsActivity_;
import com.khmelenko.lab.mester.activity.management.TestcasesActivity_;
import com.khmelenko.lab.mester.activity.testing.NewTestingActivity_;
import com.khmelenko.lab.mester.activity.testing.TestActivity_;
import com.khmelenko.lab.mester.activity.testing.TestingDetailsActivity_;
import com.khmelenko.lab.mester.activity.testing.TestingResultsActivity_;
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
                NewTestingActivity_.class, TestActivity_.class, TestingDetailsActivity_.class}
)
public class NetworkModule {

    @Provides
    @Singleton
    public RestClient provideRestClient() {
        return new RestClientRetrofit();
    }
}

